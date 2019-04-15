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
	
	alert("adding room");
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