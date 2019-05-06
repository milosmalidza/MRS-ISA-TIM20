var controllerPath = "/hotelHome";

function makeReservation() {
	
	//check if the user is logged in
	let user = window.localStorage.getItem("user");
	
	if(user === "" || user === undefined || user === null) {
		toast("Please login or register in order to proceed");
		return;
	}
	
	//TODO: prikazi slobodne sobe
	displayAvailableRooms();
	
	//TODO: prikazi dodatne usluge
	
}


function displayAvailableRooms() {
	
	$("#available-rooms-table").show();
	
	document.getElementById("reservation-div").style.height = "100%";
	
}