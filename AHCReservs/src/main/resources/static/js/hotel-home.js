var controllerPath = "/hotelHome";

var roomCheckInDate = null;
var roomCheckOutDate = null;


function makeReservation() {
	
	//check if the user is logged in
	let user = window.localStorage.getItem("user");
	
	if(user === "" || user === undefined || user === null) {
		toast("Please login or register in order to proceed");
		return;
	}
	
	//toggle pop up div
	showReservationDiv();
	
	
}

function getAvailableRooms() {
	
	if(roomCheckInDate == null || roomCheckOutDate == null) {
		toast("Please select reservation date first");
		return;
	}
	
	
	//get available rooms from server
	axios.post(controllerPath + "/getAvailableRooms", getDatesAndHotelJson())
		.then(response => {
			
			if(response.data != "" && response.data != null) {
				addAllRoomsToTable(response.data);
				$("#available-rooms-table").show();
			}
		});
	
}

function getDatesAndHotelJson() {
	
	return {
		"companyID": $("#main-content-holder").attr("data-hotel-id"),
		"strStartDate": roomCheckInDate,
		"strEndDate": roomCheckOutDate
	};
}
	

function addAllRoomsToTable(rooms) {
	
	$("#available-rooms-body").empty();
	
	for(let i = 0; i < rooms.length; i++) {
		addRoomToTable(rooms[i]);
	}
	
}


function addRoomToTable(room) {
		
	var tableRef = $("#available-rooms-body")[0];
	
	// Insert a row in the table at the last row
	var newRow = tableRef.insertRow(tableRef.rows.length);
	
	let index = 0; //table column index
	
	//iterate through room properties
	for (var property in room) {
	    if (room.hasOwnProperty(property)) {
	    	
	    	//id won't be displayed, reservation details also won't be displayed
	    	if(property === 'id' || property === 'reserved' || property === 'reservation') {
	    		continue;
	    	}
	    	
	    	// Insert a cell in the row at index
	    	var newCell  = newRow.insertCell(index);
	    	
	    	//room price is an object containing the price and currency
	    	if(property === 'roomPrice') {
	    		var newText = document.createTextNode(room[property].price + ' ' + room[property].currency);
	    		newCell.appendChild(newText);
	    	} else {
	    		// Append a text node to the cell
		    	var newText  = document.createTextNode(room[property]);
		    	newCell.appendChild(newText);
	    	}
	        	
	    	index = index + 1;
	    }
	}
	
	
	//create  checkbox
	var checkBoxCell  = newRow.insertCell(index);
	
	var checkbox = document.createElement('input');
	checkbox.type = "checkbox";
	checkbox.id = "id";
	checkbox.setAttribute("data-room-id", room.id);
	
	checkBoxCell.appendChild(checkbox);
}


function displayAdditionalServices() {
	
	
	//get additional services from server
	let hotelID = $("#main-content-holder").attr("data-hotel-id");
	
	let hotelIDJson = { "key": hotelID };
	
	axios.post(controllerPath + "/getHotelAdditionalServices", hotelIDJson)
		.then(response => {
			
			if(response.data != null) {
				
				
				if(response.data.length === 0) {
					toast("The hotel does not have any additional services yet");
					return;
				}
				
				let serviceTypesAndPrices = []
				
				//iterate through additional services
				for(let i = 0; i < response.data.length; i++) {
					
					//store the service type and price as a string to array
					serviceTypesAndPrices.push(response.data[i].service + ':   ' +
							response.data[i].servicePrice.price + ' ' + response.data[i].servicePrice.currency); 
					
				}
				
				//add the strings to the multi select tag
				addValuesToSelect("#additional-services-select", serviceTypesAndPrices, "Additional services...");
				
			}
			
		});
	
		$("#additional-services-table").show();
}


function reserveRooms() {
	
	var checkedRooms = getCheckedRooms("#available-rooms-table");
	
	if(checkedRooms.length === 0) {
		toast("You must select at least 1 room in order to reserve it");
		return;
	}
	
	if(roomCheckInDate == null || roomCheckOutDate == null) {
		toast("Please select reservation date first");
		return;
	}
	
	if($("#guests-num").val() === "") {
		toast("You haven't selected the number of guests");
		return;
	}
	
	
	axios.post(controllerPath + "/reserveRooms", getReservationJson(checkedRooms))
		.then(response => {
			toast(response.data);
			
			getAvailableRooms(); //update changes in table
		});
	
	
}

function getReservationJson(checkedRooms) {
	
	
	let additionalServices = $("#additional-services-select").val();
	
	return {
		"hotelID": $("#main-content-holder").attr("data-hotel-id"),
		"strCheckInDate": roomCheckInDate,
		"strCheckOutDate": roomCheckOutDate,
		"numOfGuests": $("#guests-num").val(),
		"username": JSON.parse(window.localStorage.getItem("user")).username,
		"selectedRooms": checkedRooms,
		"selectedAdditionalServices": additionalServices
	}
	
}

function getCheckedRooms(tableID) {
	
	var checkedRooms = [];
	
	//iterate through table checkboxes
	$(tableID + " :input[type='checkbox']").each(function() {
		
		var checkbox = $(this);
		
		if(checkbox.is(':checked')) {
			
			checkedRooms.push(checkbox.context.attributes["data-room-id"].value);
			
		}
		
	});
	
	return checkedRooms;
	
}


function showReservationDiv() {
	
	$("#reservation-date-table").show(); //show date picker
	
	//hide the tables in case they're displayed
	$("#available-rooms-table").hide();
	$("#additional-services-table").hide();
	
	
	document.getElementById("reservation-div").style.height = "100%";
	
}

function hideReservationDiv() {
	
	document.getElementById("reservation-div").style.height = "0%";
	
}