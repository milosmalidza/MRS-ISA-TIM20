var controllerPath = "/hotelAdmin";
var adminHotel = null;

window.onload = function() {
	
	//get the currently logged in user
	let user = window.localStorage.getItem("user");
	
	if(user === null || user === undefined || user === "") {
		window.location.href = "index.html";
	}
	
	let loggedUser = JSON.parse(user);
	let usernameJson = { "username": loggedUser.username }; //json used for posting a request
	
	//get the hotel of the current user
	axios.post(controllerPath + "/getHotel", usernameJson)
		.then(response => {
			
			if(response.data != "" && response.data != null) {
				adminHotel = response.data;
				fillHotelProfileInputs(response.data); //fill the profile data
				
				for(let i = 0; i < adminHotel.rooms.length; i++) {
					addRoomToTable(adminHotel.rooms[i]);
				}
				
				
			} else {
				alert("You haven't been assigned a hotel yet");
				
				//redirect the user to the home page and invalidate his session
				window.localStorage.setItem("user", "");
				window.location.href = "index.html";
				
			}
		});
}


function fillHotelProfileInputs(hotel) {
	
	$("#hotel-name").val(hotel.name);
	
	if(hotel.description != null) {
		$("#hotel-description").val(hotel.description);
	}
	
	if(hotel.address != null) {
		$("#hotel-address").val(hotel.address);
	}
	
	//TODO: room configuration
	
	//TODO: pricing
	
}

//update the hotel profile
function updateProfile() {
	
	if(validateInputFields("#hotel-profile-table") === false) {
		toast("Empty fields detected");
		return;
	}
	
	axios.post(controllerPath + "/updateProfile", getHotelJson())
		.then(response => {
			
			if(response.data === null || response.data === "") {
				toast("Hotel name already exists");
				return;
			}
			
			adminHotel = response.data; //update the global hotel variable
			fillHotelProfileInputs(response.data);
		});
	
		
}

function getHotelJson() {
	
	return {
		"id": adminHotel.id,
		"name": $("#hotel-name").val(),
		"description": $("#hotel-description").val(),
		"address": $("#hotel-address").val()
	};
}


function addRoom() {
	
	if(validateInputFields("#add-room-table") === false) {
		toast("Empty fields detected");
		return;
	}
	
	//TODO: calculate number of beds, compare them to the room type selected
	
	axios.post(controllerPath + "/addRoom", getRoomJson())
		.then(response => {
			
			if(response.data === null || response.data === "") {
				toast("Room with the room number already exists");
				return;
			}
			
			// append a row to the all rooms table
			addRoomToTable(response.data);
			
		}); 
	
	
}


function getRoomJson() {
	
	return {
		"hotelID": adminHotel.id,
		"number": $("#room-number").val(),
		"floor": $("#room-floor").val(),
		"numOfBeds": $("#room-beds").val(),
		"roomTypeString": $("#room-type").val()
	};
	
}


function addRoomToTable(room) {
	
	var tableRef = $("#rooms-table-body")[0];
	
	// Insert a row in the table at the last row
	var newRow   = tableRef.insertRow(tableRef.rows.length);
	
	//iterate through room properties
	let index = 0;
	for (var property in room) {
	    if (room.hasOwnProperty(property)) {
	        
	    	//skip the room id when displaying it
	    	if(property === 'id') {
	    		continue;
	    	}
	    	
	    	// Insert a cell in the row at index 0
	    	var newCell  = newRow.insertCell(index);

	    	// Append a text node to the cell
	    	var newText  = document.createTextNode(room[property]);
	    	newCell.appendChild(newText);
	    	
	    	index = index + 1;
	    }
	}
	
}

//display tables containing room configuration forms
function showForm() {
	
	$("#add-room-table").toggle();
	$("#rooms-table").toggle();
}


function validateInputFields(tableID) {
	
	var valid = true;
	$(tableID + " :input").not(":button").each(function() {
		
		var input = $(this).val();
		
		if(input === "" || input === null) {
			valid = false;
		}
		
	});
	
	return valid;
}