

function initHomePage() {
	var rotatedEl = document.getElementById("absolute-div-1");
	
	var leaf = document.getElementById("leaf-animation");
	
	if (sessionUser != null && sessionUser != undefined) {
		if (sessionUser.user == "registeredUser") {
			document.getElementById("my-reservations").style.display = "block";
		}
	}
	
	$("body").removeClass("preload");
	
	leaf.style.animationName = "leafStrokeFinish";
	leaf.style.animationDuration = "2s";
	leaf.style.animationIterationCount = "1";
	setTimeout(function() {
		leaf.style.transition = "fill 1s";
		leaf.style.webkitTransition = "fill .5s";
		leaf.style.fill = "rgba(232,204,81,1.00)";
	}, 1900);
	
	setTimeout(function() {
		rotatedEl.style.width = "45%";
		
		window.addEventListener("wheel", MouseWheelHandler, false);
		
	}, 100);
}

var wheelTriggered = false;

var is_second_div_up = false;

function MouseWheelHandler(e) {
	if (viewingReservations) return;
	if (wheelTriggered) return;
	
	
	var wheelDirection = e.wheelDelta;
	
	if (wheelDirection < 0) {
		scrolledDown();
	}
	else {
		scrolledUp();
	}
}


function scrolledDown() {
	if (is_second_div_up) return;
	is_second_div_up = true;
	wheelTriggered = true;
	
	var teaser = document.getElementById("white-teaser");
	teaser.style.transition = "ease-in .6s";
	teaser.style.webkitTransition = "ease-in .6s";
	teaser.style.top = "0%";
	
	setTimeout(function() {
		wheelTriggered = false;
		document.getElementById("third-div").style.display = "none";
		document.getElementById("secondary-div").style.display = "block";
		teaser.style.transition = "ease-out .6s";
		teaser.style.webkitTransition = "ease-out .6s";
		teaser.style.top = "-100%";
		
		
		
	}, 620);
	
	
}

function scrolledUp() {
	
	if (!is_second_div_up) return;
	is_second_div_up = false;
	wheelTriggered = true;
	
	var teaser = document.getElementById("white-teaser");
	teaser.style.transition = "ease-in .6s";
	teaser.style.webkitTransition = "ease-in .6s";
	teaser.style.top = "0%";
	
	
	setTimeout(function() {
		wheelTriggered = false;
		document.getElementById("third-div").style.display = "block";
		document.getElementById("secondary-div").style.display = "none";
		teaser.style.transition = "ease-out .6s";
		teaser.style.webkitTransition = "ease-out .6s";
		teaser.style.top = "100%";
		
		
	}, 620);
	
}


function mouseEnterItem(element) {
	var animatedDiv = element.getElementsByClassName("item-animation-div")[0];
	animatedDiv.style.height = "100%";
}



function mouseLeaveItem(element) {
	var animatedDiv = element.getElementsByClassName("item-animation-div")[0];
	animatedDiv.style.height = "0%";
}


var viewingReservations = false;
function showMyReservations() {
	var mainPageContent = document.getElementById("main-page-content");
	mainPageContent.style.webkitTransform = "translateX(-100%)";
	mainPageContent.style.transform = "translateX(-100%)";
	
	$("#nav-bar-selection-1").fadeOut(200);
	$("#back-reservation").fadeIn(200);
	viewingReservations = true;
}

function closeMyReservations() {
	var mainPageContent = document.getElementById("main-page-content");
	mainPageContent.style.webkitTransform = "translateX(0%)";
	mainPageContent.style.transform = "translateX(0%)";
	
	$("#nav-bar-selection-1").fadeIn(200);
	$("#back-reservation").fadeOut(200);
	viewingReservations = false;
}




var selectedElement = null;
function showReservations(element) {
	if (element == selectedElement) return;
	
	if (selectedElement != null) {
		selectedElement.classList.remove("white-background");
		selectedElement.classList.remove("black-color");

		selectedElement.classList.add("transparent-background");
		selectedElement.classList.add("white-color");
	}
	
	element.classList.add("white-background");
	element.classList.add("black-color");
	
	element.classList.remove("transparent-background");
	element.classList.remove("white-color");
	
	if (element.getAttribute("data-type") == "car") {
		showVehicleReservations();
	}
	else if (element.getAttribute("data-type") == "hotel"){
		showHotelReservtions();
	}
	
	selectedElement = element;
}

function showHotelReservtions() {
	$.ajax({
		type : "post",
		url : "user/getHotelReservations",
		data : {user : JSON.stringify(sessionUser)},
		success : function(response) {
			
			console.log(response);
			
			if (response == "badRequest") {
				alert("Something went wrong");
				return;
			}
			
			var table = document.getElementById("reservations-table");
			var items = JSON.parse(response);
			
			table.innerHTML = "";
			
			var firstRow = document.createElement("tr");
			
			firstRow.innerHTML = "<td>Hotel name</td><td>Room</td><td>Start date</td><td>End date</td><td>Action</td>";
			
			table.appendChild(firstRow);
			
			for (var i = 0; i < items.length; i++) {
				var status = items[i].status == "notAllowed" ? "Not allowed" : "<input class='cancel-reservation-button' type='button' value='Cancel' onclick='cancelHotelReservation(this)' />";
				
				
				
				var row = document.createElement("tr");
				row.setAttribute("data-id", items[i].id);
				row.innerHTML = "<td>" + items[i].hotelName + "</td>" +
								"<td>" + items[i].room + "</td>" + 
								"<td>" + items[i].startDate + "</td>" +
								"<td>" + items[i].endDate + "</td>" +
								"<td>" + status + "</td>";
				table.appendChild(row);
				
			}
			
			
			
		}
	});
}

function showVehicleReservations() {
	
	$.ajax({
		type : "post",
		url : "user/getVehicleReservations",
		data : {user : JSON.stringify(sessionUser)},
		success : function(response) {
			
			console.log(response);
			
			if (response == "badRequest") {
				alert("Something went wrong");
				return;
			}
			
			var table = document.getElementById("reservations-table");
			var items = JSON.parse(response);
			
			table.innerHTML = "";
			
			var firstRow = document.createElement("tr");
			
			firstRow.innerHTML = "<td>Car name</td><td>Start date</td><td>End date</td><td>Start location</td><td>End location</td><td>Action</td>";
			
			table.appendChild(firstRow);
			
			for (var i = 0; i < items.length; i++) {
				var status = items[i].status == "notAllowed" ? "Not allowed" : "<input class='cancel-reservation-button' type='button' value='Cancel' onclick='cancelCarReservation(this)' />";
				
				
				
				var row = document.createElement("tr");
				row.setAttribute("data-id", items[i].id);
				row.innerHTML = "<td>" + items[i].carName + "</td>" +
								"<td>" + items[i].startDate + "</td>" + 
								"<td>" + items[i].endDate + "</td>" +
								"<td>" + items[i].startLocation + "</td>" +
								"<td>" + items[i].endLocation + "</td>" +
								"<td>" + status + "</td>";
				table.appendChild(row);
				
			}
			
			
			
		}
	});
	
}

function cancelHotelReservation(element) {
	var row = element.parentNode.parentNode;
	
	var json = {
		id : row.getAttribute("data-id")
	};
	
	$.ajax({
		type : "post",
		url : "user/cancelHotelReservation",
		data : {json : JSON.stringify(json), user : JSON.stringify(sessionUser)},
		success : function(response) {
			if (response == "success") {
				notify("Success", "You have successfully canceled reservation");
				row.parentNode.removeChild(row);
			}
			
			else if (response == "badRequest") {
				notify("Bad request", "Something went wrong, try again later");
			}
			
			else if (response == "notAllowed") {
				notify("Cancel error", "Can not cancel reservation due to time period");
			}
		}
	});
}


function cancelCarReservation(element) {
	
	var row = element.parentNode.parentNode;
	
	var json = {
		id : row.getAttribute("data-id")
	};
	
	$.ajax({
		type : "post",
		url : "user/cancelVehicleReservation",
		data : {json : JSON.stringify(json), user : JSON.stringify(sessionUser)},
		success : function(response) {
			if (response == "success") {
				notify("Success", "You have successfully canceled reservation");
				row.parentNode.removeChild(row);
			}
			
			else if (response == "badRequest") {
				notify("Bad request", "Something went wrong, try again later");
			}
			
			else if (response == "notAllowed") {
				notify("Cancel error", "Can not cancel reservation due to time period");
			}
		}
	});
	
}






































