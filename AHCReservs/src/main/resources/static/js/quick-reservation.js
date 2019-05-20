var controllerPath = "/regUserSvc";

function changeDisplay() {
	
	
	$(".displayed-element").hide();
	
	let selectedCompany = $("#company-type").val();
	
	switch(selectedCompany) {
	
	case "hotel":
		$("#room-reservations-table").show();
		break;
		
	case "rent-a-car":
		//$("#room-reservation-table").show();
		break;
	
	}
	
}


function reserveRoom(reservationID) {
	
	alert(reservationID);
}


function addServices(reservationID) {
	
	alert(reservationID);
	
}
