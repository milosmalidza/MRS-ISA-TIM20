var controllerPath = "/hotelAdmin";
var adminHotel = null;

window.onload = function() {
	
	//get the currently logged in user
	let user = window.localStorage.getItem("user");
	
	if(user === null || user === undefined || user === "") {
		window.location.href = "index.html";
	}
	
	
	let loggedUser = JSON.parse(user);
	
	//check the type of the user
	if(loggedUser.user != "hotelAdmin") {
		history.back(); //redirect the user to the previous page
	}
	
	getAdminHotel(user);
	
}


function getAdminHotel(user) {
	
	let loggedUser = JSON.parse(user);
	let usernameJson = { "username": loggedUser.username }; //json used for posting a request
	
	//get the hotel of the logged in admin
	axios.post(controllerPath + "/getHotel", usernameJson)
		.then(response => {
			
			if(response.data != "" && response.data != null) {
				adminHotel = response.data;
				fillHotelProfileInputs(response.data); //fill the profile data
				
				//display rooms and prices
				addAllRoomsToTable(); 
				addAllServicesToTable();
				
				fillSelectInputs();
				
			} else {
				alert("You haven't been assigned a hotel yet");
				
				//redirect the user to the home page and invalidate his session
				window.localStorage.setItem("user", "");
				window.location.href = "index.html";
				
			}
		});
	
}

function getRoomTypes(selectID) {
	
	axios.get(controllerPath + "/getRoomTypes")
		.then(response => {
			addValuesToSelect("#room-type", response.data, "Select type");
			addValuesToSelect("#room-type-edit", response.data, "Select type");
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
	
}

function fillSelectInputs() {
	
	//room configuration selects
	getRoomTypes(); //fill the values in the select that contains room types

	
	axios.get(controllerPath + "/getServiceTypes")
		.then(response => {
			addValuesToSelect("#service-type", response.data, 'Select service');
		});
	
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
			toast("Updated");
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


function roomTypeBedNumValidation(roomType, numOfBeds) {
	
	switch(roomType.toLowerCase()) {
	
	case "single":
		
		if(numOfBeds != 1) {
			return "Single room can contain only one bed";
		}
		break;
		
	case "double":
		
		if(numOfBeds < 1 || numOfBeds > 2) {
			return "Double room can contain 1 or 2 beds";
		}
		break;
		
	case "triple":
		
		if(numOfBeds < 2 || numOfBeds > 3) {
			return "Triple room can contain 2 or 3 beds";
		}
		break;
		
	case "quad":
		
		if(numOfBeds < 2 || numOfBeds > 4) {
			return "Quad room can contain 2,3 or 4 beds";
		}
		break;
	} 
	
	return "ok";
	
}

function addRoom() {
	
	if(validateInputFields("#add-room-table") === false) {
		toast("Empty fields detected");
		return;
	}
	
	
	//room floor,number and price must be > 1
	if($("#room-floor").val() < 1 || $("#room-number").val() < 1 || $("#room-price").val() < 1) {
		toast("Entered numbers must be positive");
		return;
	} 
	
	//Comparing room type to number of beds
	let message = roomTypeBedNumValidation($("#room-type").val(), $("#room-beds").val());
	
	if(message != "ok") {
		toast(message);
		return;
	}
	
	axios.post(controllerPath + "/addRoom", getRoomJson())
		.then(response => {
			
			if(response.data === null || response.data === "") {
				toast("Room with the room number already exists");
				return;
			}
			
			// append a row to the all rooms table
			adminHotel.rooms.push(response.data);
			addRoomToTable(response.data);
			
			clearInputFields("#add-room-table");
			
		}); 
	
	
}


function getRoomJson() {
	
	return {
		"hotelID": adminHotel.id,
		"number": $("#room-number").val(),
		"floor": $("#room-floor").val(),
		"numOfBeds": $("#room-beds").val(),
		"roomTypeString": $("#room-type").val(),
		"price": $("#room-price").val()
	};
	
}


function addAllRoomsToTable() {
	
	$("#rooms-table-body").empty();
	
	for(let i = 0; i < adminHotel.rooms.length; i++) {
		addRoomToTable(adminHotel.rooms[i]);
	}
}

function addRoomToTable(room) {
	
	var tableRef = $("#rooms-table-body")[0];
	
	// Insert a row in the table at the last row
	var newRow = tableRef.insertRow(tableRef.rows.length);
	
	let index = 0; //table column index
	
	//iterate through room properties
	for (var property in room) {
	    if (room.hasOwnProperty(property)) {
	    	
	    	//id won't be displayed
	    	if(property === 'id') {
	    		continue;
	    	}
	    	
	    	// Insert a cell in the row at index
	    	var newCell  = newRow.insertCell(index);
	    	
    		var newText;
    		
    		if(property === 'roomPrice') {
    			newText = document.createTextNode(room[property] + ' \u20AC'); //append the currency to the price
    		} else {
    			newText = document.createTextNode(room[property]);
    		}
    		
    		newCell.appendChild(newText);
	    	
	        	
	    	index = index + 1;
	    }
	}
    
    //add the edit button to the table
    var editBtnCell  = newRow.insertCell(index);
	var editBtn = document.createElement("INPUT");
	
	editBtn.type = "button";
	editBtn.value = "Edit";
	editBtn.className = "ui primary basic button";
    
	editBtn.setAttribute("onclick", "displayEditRoomModal(this);");
	editBtn.setAttribute("data-room-id", room.id); //bind room id to the button
    
    editBtnCell.appendChild(editBtn);
    
    
    //add the remove button to the table
	var removeBtnCell  = newRow.insertCell(index + 1);
	var btnRemove = document.createElement("INPUT");
	
    btnRemove.type = "button";
    btnRemove.value = "Remove";
    btnRemove.className = "ui negative basic button";
    
    btnRemove.setAttribute("onclick", "removeRoom(this);");
    btnRemove.setAttribute("data-room-id", room.id); //bind room id to the button
    
    removeBtnCell.appendChild(btnRemove);
	
}


function getRoom(roomID) {
	
	let roomToReturn = null;
	//find the room which is being edited
	for (let i = 0; i < adminHotel.rooms.length; i++) {
		if(adminHotel.rooms[i].id == roomID) {
			roomToReturn = adminHotel.rooms[i];
			break;
		}
	}
	
	return roomToReturn;
}


function displayEditRoomModal(editBtn) {
	
	
	$("#edit-service-table").hide(); //hide the form for editing a service
	$("#edit-room-table").show();
	
	//Add the room id to the hidden input field
	let roomID = editBtn.getAttribute("data-room-id");
	$("#edit-room-id").val(roomID);
	
	
	let roomToDisplay = getRoom(roomID);
	
	if(roomToDisplay.reserved === true) {
		toast("Can't edit a reserved room");
		return;
	}
	
	//display modal
	var modal = document.getElementById('edit-modal');
	modal.style.display = "block";
	
	
	//display the rooms data
	$("#room-floor-edit").val(roomToDisplay.floor);
	$("#room-number-edit").val(roomToDisplay.number);
	
	// TODO: postavi tekucu vrednost $('#room-type-edit').dropdown('set selected', roomToDisplay.roomType);
	
	$("#room-beds-edit").val(roomToDisplay.numOfBeds);
	$("#room-price-edit").val(roomToDisplay.roomPrice);
	
	addListenerToEditModal(modal);

}

function addListenerToEditModal(modal) {
	
	//When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	  if(event.target == modal) {
		  modal.style.display = "none";
	  }
	}
	
}


function closeEditModal() {
	
	var modal = document.getElementById('edit-modal');
	modal.style.display = "none";
	
}


function removeRoom(removeBtn) {
	
	let roomID = removeBtn.getAttribute("data-room-id");
	
	let roomToRemove = getRoom(roomID);
	
	//TODO: proveri na serveru da li je soba rezervisana
	
	if(roomToRemove.reserved === true) {
		toast("Can't remove a reserved room");
		return;
	}
	
	if(confirm("Are you sure you wan't to delete the room?") === false) {
		return;
	}
	
	roomKeyJSON = {"key": roomID};
	
	//sending request to server
	axios.post(controllerPath + "/removeRoom", roomKeyJSON)
		.then(response => {
		
			if(response.data.toLowerCase() != "success") {
				toast(response.data);
				return;
			}
			
			
			$("#rooms-table-body").empty(); //remove all items from the table
			
			let user = window.localStorage.getItem("user");
			getAdminHotel(user); //re-get the hotel and redraw the rooms in table
		
	});
	
}


function editRoom() {

	
	if(validateInputFields("#edit-room-table") === false) {
		toast("Empty fields detected");
		return;
	}
	
	//room floor and number must be > 1
	if($("#room-floor-edit").val() < 1 || $("#room-number-edit").val() < 1) {
		toast("Entered numbers must be positive");
		return;
	}
	
	//Comparing room type to number of beds
	let message = roomTypeBedNumValidation($("#room-type-edit").val(), $("#room-beds-edit").val());
	
	if(message != "ok") {
		toast(message);
		return;
	}
	
	
	let roomID = $("#edit-room-id").val();

	//sending request to server
	axios.post(controllerPath + "/editRoom", getEditRoomJson(roomID))
		.then(response => {
		
			if(response.data.toLowerCase() != "success") {
				toast(response.data);
				return;
			}
				
			emptyAndRedrawTable("#rooms-table-body");
		
		});
	
}


function getEditRoomJson(roomID) {
	
	return {
		"hotelID": adminHotel.id,
		"roomID": roomID,
		"number": $("#room-number-edit").val(),
		"floor": $("#room-floor-edit").val(),
		"numOfBeds": $("#room-beds-edit").val(),
		"roomTypeString": $("#room-type-edit").val(),
		"price": $("#room-price-edit").val()
	};
	
}

//display tables containing room configuration forms
function displayRoomConfig() {
	
	//show content
	$("#add-room-table").show();
	$("#rooms-table").show();
	
	//add the animation
	document.getElementById("room-configuration-div").style.width = "100%";
}

function displayPriceConfig() {
	
	$("#add-service-table").show();
	
	//add the animation
	document.getElementById("price-configuration-div").style.width = "100%";
}


function hideRoomConfig() {
	
	//adding the animation
	document.getElementById("room-configuration-div").style.width = "0%";
	
	//hide content
	$("#add-room-table").hide();
	$("#rooms-table").hide();
	
}

function hidePriceConfig() {
	
	$("#add-service-table").hide();
	
	//add the animation
	document.getElementById("price-configuration-div").style.width = "0%";
}


function addService() {
	
	if(validateInputFields("#add-service-table") === false) {
		toast("Empty field detected");
		return;
	}
	
	if($("#service-price").val() < 1) {
		toast("Service price must be > 0");
		return;
	}
	
	
	axios.post(controllerPath + "/addService", getServiceJson())
		.then(response => {
			
			if(response.data === "" || response.data === null) {
				toast("Service already added");
				return;
			}
			
			addRowToServicesTable(response.data);
			adminHotel.pricelist.push(response.data);
			
		});
	
}

function addRowToServicesTable(serviceObject) {
	
	
	let tableRef = $("#all-services-body")[0];
	
	// Insert a row in the table at the last row
	let newRow = tableRef.insertRow(tableRef.rows.length);
	
	//add a cell for service type
	let newCell  = newRow.insertCell(0);
	let newText  = document.createTextNode(serviceObject.service);
	newCell.appendChild(newText);
	
	//add a cell for service price
	newCell = newRow.insertCell(1);
	newText = document.createTextNode(serviceObject.servicePrice + ' \u20AC');
	newCell.appendChild(newText);
	
	
    //add the edit button to the table
    var editBtnCell  = newRow.insertCell(2);
	var editBtn = document.createElement("INPUT");
	
	editBtn.type = "button";
	editBtn.value = "Edit";
	editBtn.className = "ui primary basic button";
    
	editBtn.setAttribute("onclick", "displayEditServiceModal(this);");
	editBtn.setAttribute("data-service-id", serviceObject.id); //bind service id to the button
    
    editBtnCell.appendChild(editBtn);
   
}


function displayEditServiceModal(editBtn) {
	
	
	$("#edit-room-table").hide(); //hide the form for editing a room
	$("#edit-service-table").show(); //show the form for editing a service
	
	//Add the service id to the hidden input field
	let serviceID = editBtn.getAttribute("data-service-id");
	$("#edit-service-id").val(serviceID);
	
	
	let serviceToEdit = getService(serviceID);
	
	//display modal
	var modal = document.getElementById('edit-modal');
	modal.style.display = "block";
	
	
	//display the service data
	$("#edit-service-type").val(serviceToEdit.service);
	$("#edit-service-price").val(serviceToEdit.servicePrice);
	
	addListenerToEditModal(modal);

}


function getService(serviceID) {
	
	for(let i = 0; i < adminHotel.pricelist.length; i++) {
		
		if(adminHotel.pricelist[i].id == serviceID) {
			return adminHotel.pricelist[i];
		}
		
	}
	
}

function addAllServicesToTable() {
	
	$("#all-services-body").empty();
	
	for(let i = 0; i < adminHotel.pricelist.length; i++) {
		addRowToServicesTable(adminHotel.pricelist[i]);
	}
	
}

function editService() {
	
	
	if(validateInputFields("#edit-service-table") === false) {
		toast("Empty field detected");
		return;
	}
	
	
	axios.post(controllerPath + "/editService", getEditServiceJson())
		.then(response => {
			
			emptyAndRedrawTable("#all-services-body");
			
		});
	
}

/** Empties the table, re-gets the hotel of the logged in user in order to redraw the table after editing */
function emptyAndRedrawTable(tableID) {
	
	$(tableID).empty(); //remove all items from the table
	
	let user = window.localStorage.getItem("user");
	getAdminHotel(user); //re-get the hotel and redraw the services in table
	
	closeEditModal(); //close the pop-up window for editing
	
}


function getEditServiceJson() {
	
	return {
		"serviceID": $("#edit-service-id").val(),
		"serviceTypeString": $("#edit-service-type").val(),
		"price": $("#edit-service-price").val()
	}
	
}

function getServiceJson() {
	
	return {
		"hotelID": adminHotel.id,
		"serviceTypeString": $("#service-type").val(),
		"price": $("#service-price").val()
	};
}
