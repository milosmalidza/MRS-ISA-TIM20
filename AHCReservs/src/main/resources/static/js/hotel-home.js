var controllerPath = "/hotelHome";

var numOfNights = 0;
var roomCheckInDate = null;
var roomCheckOutDate = null;

var totalPrice = 0;
var additionalServicesPrice = 0;
var numOfCheckedRooms = 0;

var additionalServices = null;

var pinAdded = false;
var dateValid = true;


function getMap() {
	
	let hotelID = $("#main-content-holder").attr("data-hotel-id");
	
	let keyJson = {"key": hotelID};
	
	axios.post(controllerPath + "/getHotel", keyJson)
		.then(response => {
			
			if(loadMap(response.data, "hotel", false) == null) {
				pinAdded = false;
			} else {
				pinAdded = true;
			}
		});
	
}


function viewHotelLocation() {
	
	if(pinAdded) {
		$(".initially-hidden-element").toggle();
	} else {
		notify("Map service", "The hotel location hasn't been added yet");
	}
	
}


function initializeDatePicker(dataAttrHolder, dataAttrID) {
	
	$(function() {
		$('input[name="daterange"]').daterangepicker({
			timePicker: false,
	    	opens: 'left',
	    	minDate: new Date()
	  	}, function(start, end, label) {
	           
		       roomCheckInDate = start.format('DD.MM.YYYY.');
		       roomCheckOutDate = end.format('DD.MM.YYYY.');
		       
		       let fileName = location.href.split("/").slice(-1); 
		       
			if ($("#company-type").val() == undefined || $("#company-type").val() == "hotel") {
				
				numOfNights = end.diff(start, "days"); 
				console.log(numOfNights);

		         if(numOfNights === 0) {
		           notify("Hotel service", "You must rent a room at least for one night");
		           dateValid = false;
		           return;
		         } else {
		           dateValid = true;
		         }
		
		         additionalServicesPrice = 0;
		         totalPrice = 0;
		         numOfCheckedRooms = 0;
		
		         let hotelID = $(dataAttrHolder).attr(dataAttrID);
				 getAvailableRooms(hotelID);
				 
			} else if ($("#company-type").val() == "rent-a-car") {
				
				var rentACarID = $(dataAttrHolder).attr(dataAttrID);
				$.ajax({
					type : "post",
					url : sysAdminControllerPath + "/returnServiceCars",
					data : {json : JSON.stringify({
						companyid : rentACarID,
						startDate : roomCheckInDate,
						endDate : roomCheckOutDate
					})},
					success : function(response) {
						var data = JSON.parse(response);
						listServiceVehicles(data);
					}
				});
				
			}
			
		       
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
				notify("Room reservation", "No rooms available at the moment");
				$("#available-rooms-body").empty();
				$("#additional-services-select").dropdown('clear');
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
	checkbox.setAttribute("data-room-id", room.id);
	checkbox.addEventListener("click", roomChecked, false);
	
	checkBoxCell.appendChild(checkbox);
}


function roomChecked(e) {
	
	var caller = e.target || e.srcElement;
    let roomId = caller.getAttribute("data-room-id");
    
    let keyJson = { "key": roomId };
    axios.post(controllerPath + "/getRoom", keyJson)
    	.then(response => {
    			
			if(caller.checked === true) {
				
				if(numOfCheckedRooms == 0) {
					totalPrice += response.data.roomPrice * numOfNights;
				} else {
					totalPrice += (response.data.roomPrice  + additionalServicesPrice) * numOfNights;
				}
				
				numOfCheckedRooms += 1;
				
			} else {
				
				if(numOfCheckedRooms == 1) {
					totalPrice -= response.data.roomPrice * numOfNights;
				} else {
					totalPrice -= (response.data.roomPrice + additionalServicesPrice) * numOfNights;
				}
				
				numOfCheckedRooms -= 1;
			}
			
			$("#total-price").html(totalPrice + ' &euro;');
    		
    	});
	
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
				
				additionalServices = response.data;
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
	
	if(dateValid === false) {
		notify("Hotel service", "You must rent a room at least for one night");
		return;
	}
	
	if(checkedRooms.length === 0) {
		toast("You must select at least 1 room in order to reserve it");
		return;
	}
	
	if(roomCheckInDate == null || roomCheckOutDate == null) {
		toast("Please select reservation date first");
		return;
	}
	
	if($("#guests-num").val() === "" || $("#guests-num").val() < 1) {
		toast("Please select a valid number of guests");
		return;
	}
	
	let hotelID = $(dataHolder).attr(dataAttr);
	
	axios.post(controllerPath + "/reserveRooms", getReservationJson(checkedRooms, hotelID))
		.then(response => {
			toast(response.data);
			
			getAvailableRooms(hotelID); //update changes in table
			
			numOfCheckedRooms = 0;
			additionalServicesPrice = 0;
			totalPrice = 0;
			$("#total-price").html(totalPrice + '&euro;');
			
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
		"selectedAdditionalServices": additionalServices,
		"numOfNights": numOfNights
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


function additionalServiceSelected(value) {
	
	if(numOfCheckedRooms > 0) {
		totalPrice -= additionalServicesPrice * numOfCheckedRooms * numOfNights;
	} else {
		totalPrice -= additionalServicesPrice * numOfNights;
	}
	
	additionalServicesPrice = 0;
	
	if(value == "") {
		$("#total-price").html(totalPrice + ' &euro;');
		return;
	}
	
	for(let i = 0; i < additionalServices.length; i++) {
		
		if(value.includes(additionalServices[i].service.toLowerCase())) {
			additionalServicesPrice += additionalServices[i].servicePrice;
		}
		
	}
	
	if(numOfCheckedRooms > 0) {
		totalPrice += additionalServicesPrice * numOfCheckedRooms * numOfNights;
	} else {
		totalPrice += additionalServicesPrice * numOfNights;
	}
	
	
	$("#total-price").html(totalPrice + ' &euro;');
	
}