var controllerPath = "/hotelHome";

var roomCheckInDate = null;
var roomCheckOutDate = null;



function initializeDatePicker(dataAttrHolder, dataAttrID) {
	
	$(function() {
		$('input[name="daterange"]').daterangepicker({
			timePicker: false,
	    	opens: 'left',
	    	minDate: 0
	  	}, function(start, end, label) {
	           console.log("A new date selection was made: " + start.format('YYYY-MM-DD HH-mm') + ' to ' + end.format('YYYY-MM-DD HH-mm'));
		       roomCheckInDate = start.format('DD.MM.YYYY.');
		       roomCheckOutDate = end.format('DD.MM.YYYY.');
		       
		       let hotelID = $(dataAttrHolder).attr(dataAttrID);
		       
		       getAvailableRooms(hotelID);
	    });
	});
	
	
}

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

function getAvailableRooms(hotelID) {
	
	if(roomCheckInDate == null || roomCheckOutDate == null) {
		toast("Please select reservation date first");
		return;
	}
	
	
	//get available rooms from server
	axios.post(controllerPath + "/getAvailableRooms", getDatesAndHotelJson(hotelID))
		.then(response => {
			
			if(response.data != "" && response.data != null) {
				addAllRoomsToTable(response.data);
				$("#available-rooms-table").show();
				displayAdditionalServices(hotelID);
			} else {
				toast("No rooms available at the moment");
				$("#available-rooms-body").empty();
			}
		});
	
}

function getDatesAndHotelJson(hotelID) {
	
	return {
		"companyID": hotelID,
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
		
	
	let jsonData = insertAttrToRoomTable(room, "#available-rooms-body"); 
	
	//create  checkbox
	var checkBoxCell  = jsonData.newRow.insertCell(jsonData.index);
	
	var checkbox = document.createElement('input');
	checkbox.type = "checkbox";
	checkbox.id = "id";
	checkbox.setAttribute("data-room-id", room.id);
	
	checkBoxCell.appendChild(checkbox);
}


function displayAdditionalServices(hotelID) {
	
	
	let hotelIDJson = { "key": hotelID };
	
	//get additional services from server
	axios.post(controllerPath + "/getHotelAdditionalServices", hotelIDJson)
		.then(response => {
			
			if(response.data != null) {
				
				
				if(response.data.length === 0) {
					return;
				}
				
				let serviceTypesAndPrices = []
				
				//iterate through additional services
				for(let i = 0; i < response.data.length; i++) {
					
					serviceJson = {"service": response.data[i].service, "price": response.data[i].servicePrice + ' \u20AC'};
					//store the service type and price as a string to array
					serviceTypesAndPrices.push(serviceJson); 
					
				}
				
				//add the strings to the multi select tag
				addServicesToSelect("#additional-services-select", serviceTypesAndPrices, "Additional services...");
				
			}
			
		});
	
		$("#additional-services-table").show();
}


function reserveRooms(dataHolder, dataAttr) {
	
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
	
	let hotelID = $(dataHolder).attr(dataAttr);
	
	axios.post(controllerPath + "/reserveRooms", getReservationJson(checkedRooms, hotelID))
		.then(response => {
			toast(response.data);
			
			getAvailableRooms(hotelID); //update changes in table
		});
	
	
}

function getReservationJson(checkedRooms, hotelID) {
	
	
	let additionalServices = $("#additional-services-select").val();
	
	return {
		"hotelID": hotelID,
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