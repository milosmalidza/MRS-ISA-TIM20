var controllerPath = "/regUser";

function changeDisplay() {
	
	
	$(".displayed-element").hide();
	
	let selectedCompany = $("#company-type").val();
	
	switch(selectedCompany) {
	
	case "hotel":
		$("#room-reservations-table").show();
		break;
		
	case "rent-a-car":
		$("#vehicle-reservations-table").show();
		break;
	
	}
	
}


function reserveVehicle(id, element) {
	if (sessionUser == null) {
		notify("Unsuccessful", "You must be logged in");
	}
	
	$.ajax({
		type : "post",
		url : controllerPath + "/quickVehicleReservation",
		data : {json : JSON.stringify({
			id : id
		}), user : JSON.stringify(sessionUser)},
		success : function(response) {
			$(element.parentNode.parentNode).hide();
			if (response == "success") {
				notify("Success", "You have successfully reserved a vehicle");
			}
		}
	})
}


function reserveRoom(reservationID) {
	
	let user = window.localStorage.getItem("user");
	
	if(user === "") {
		toast("Please login in order to proceed");
		return;
	}
	
	axios.post(controllerPath + "/quickRoomReservation", getQuickReservJson(reservationID, JSON.parse(user)))
		.then(response => {
			
			toast(response.data);
			
			if(response.data.toLowerCase() == "success") {
				removeReservation(reservationID);
			}
			
		}); 
	
}


function removeReservation(reservationID) {
	
	let table = document.getElementById("room-reservations-table");
	let i;
	let currentID;
	
	for (i = 0; i < table.rows.length; i++) {
		
		currentID = $(table.rows[i]).attr("data-reservation-id");
		
		if(currentID == reservationID) {
			break;
		}
		
	}
	
	
	table.deleteRow(i);
}

function getQuickReservJson(reservationID, user) {
	
	return {
		"key": reservationID,
		"value": user.username
		
	}
	
}

function addServices(reservationID) {
	
	closeAdditionalServices();
	
	axios.post(controllerPath + "/getAdditionalServices", {"key": reservationID})
		.then(response => {
			
			if(response.data != "") {
				
				addAllServicesToTable(response.data);
				$("#additional-services-table").show();
				document.getElementById("additional-services-div").style.height = "40%";
				
			} else {
				toast("No services found");
			}
			
		});
	
}

function closeAdditionalServices() {
	
	document.getElementById("additional-services-div").style.height = "0%";
	
}

function addAllServicesToTable(services) {
	
	$("#additional-services-body").empty();
	
	for(let i = 0; i < services.length; i++) {
		insertServiceToTable(services[i], "#additional-services-body");
	}
	
}
