window.addEventListener("load", function() {
	var mainDiv = document.getElementById("main-div");
	if (mainDiv != "undefined") {
		mainDiv.style.opacity = "1";
		mainDiv.style.webkitTransform = "scale(1)";
		mainDiv.style.transform = "scale(1)";
	}
	
}, false);


var vehicleReservationStartDate = null;
var vehicleReservationEndDate = null;


function searchVehicles() {
	showDataLoader();
	var form = document.getElementById("vehicle-search-form");
	var itemsHolder = document.getElementById("items-holder");
	
	
	var dateElement = document.getElementById("daterange");
	var doorsElement = document.getElementById("number-of-doors");
	var peopleElement = document.getElementById("number-of-people");
	var typeElement = document.getElementById("type-of-vehicle");
	
	var is_error = false;
	
	dateElement.classList.remove("error");
	doorsElement.classList.remove("error");
	peopleElement.parentNode.classList.remove("error");
	typeElement.classList.remove("error");
	
	if (dateElement.value == "") {
		dateElement.classList.add("error");
		is_error = true;
	}
	
	if (doorsElement.value == "" || isNaN(doorsElement.value)) {
		doorsElement.classList.add("error");
		is_error = true;
	}
	
	if (peopleElement.value == "" || isNaN(peopleElement.value)) {
		peopleElement.parentNode.classList.add("error");
		is_error = true;
	}
	
	if (typeElement.value == "") {
		typeElement.classList.add("error");
		is_error = true;
	}

	if (is_error) {
		hideDataLoader();
		return;
	}
	
	
	$.ajax({
        type:"POST",
        url: "searchVehicles",
        data:{startDate:  vehicleReservationStartDate,
			  endDate : vehicleReservationEndDate,
			 doors : parseInt(doorsElement.value),
			 people : parseInt(peopleElement.value),
			 type : typeElement.value},
        success:function(response){
            var cars = JSON.parse(response);
			if (cars.length != 0) {
				var vehicleItemsRow = document.getElementById("vehicle-items-row");
				vehicleItemsRow.innerHTML = "";
				
				var row = document.createElement("div");
				row.setAttribute("class", "row");
				
				for (var i = 0; i < cars.length; i++) {
					var itemHolder = document.createElement("div");
					itemHolder.setAttribute("class", "col-lg-3  col-sm-4 col-xs-6 vehicle-item");
					itemHolder.innerHTML =  '<div class="vehicle-item-holder">' +
												'<div class="vehicle-item-name">' + cars[i].name + '</div>' +
												'<div class="vehicle-item-description">' + cars[i].description + '</div>' +
												'<div class="vehicle-item-number-of-people bold-font">Number of people: ' + cars[i].numOfSeats + '</div>' +
												'<div class="vehicle-item-number-of-doors bold-font">Number of doors: ' + cars[i].numOfDoors + '</div>' +
												'<div class="vehicle-item-vehicle-type bold-font">Type: ' + cars[i].vehicleType + '</div>' +
												'<div class="vehicle-item-price-per-day bold-font">Price per day: ' + cars[i].pricePerDay + '</div>' +
												'<input class="ui button reserve-vehicle-button" type="button" value="Reserve" />' + 
											'</div>';
					row.appendChild(itemHolder);
				}
				
				vehicleItemsRow.appendChild(row);
				
			}
			else {
				var vehicleItemsRow = document.getElementById("vehicle-items-row");
				vehicleItemsRow.innerHTML = "<div class='no-items-found'>No vehicles found</div>";
				
			}
			hideDataLoader();
			document.body.style.overflowY = "hidden";
			itemsHolder.style.left = "0px";
        }
    });
	
	
	
}

function closeSearchVehicles() {
	var itemsHolder = document.getElementById("items-holder");
	document.body.style.overflowY = "auto";
	itemsHolder.style.left = "100%";
}

function loginUser() {
	showDataLoader();
	var loginForm = document.getElementById("single-form");
	
	var formData = new FormData(loginForm);
	
	
	$.ajax({
		type: "POST",
		url: "loginUser",
		data: {username: formData.get("username"),
			  password: formData.get("password")},
		success: function(response) {
			console.log(response);
			hideDataLoader();
		}
	})
	
}

var isPageLoading = false;
function loadPage(url) {
	var animationDiv = document.getElementById("exiting-page-animation");
	
	if (animationDiv == null) {
		window.location.href = url;
		return;
	}
	
	
	animationDiv.style.width = "100%";
	
	if (!isPageLoading) {
		isPageLoading = true;
		setTimeout(function() {
			window.location.href = url;
		}, 700);
	}
	
}


function registerUser() {
	showDataLoader();
	var registerForm = document.getElementById("single-form");
	var inputs = registerForm.getElementsByTagName("input");
	
	var emptyField = false;
	
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type != "button") {
			if (inputs[i].value == "") {
				emptyField = true;
				inputs[i].parentNode.classList.add("error");
			}
			else {
				inputs[i].parentNode.classList.remove("error");
			}
		}
	}
	
	if (emptyField) {
		showFormMessage("Please fill the required fields and proceed.", 3000);
		hideDataLoader();
		return;
	}
	
	var pass = document.getElementById("password");
	var confPass = document.getElementById("confirm-password");
	
	if (pass.value != confPass.value) {
		
		pass.parentNode.classList.add("error");
		confPass.parentNode.classList.add("error");
		
		showFormMessage("Passwords don't match.", 3000);
		hideDataLoader();
		return;
	}
	
	
	
	var formData = new FormData(registerForm);
	
	$.ajax({
		type: "POST",
		url: "registerUser",
		data: {username : formData.get("username")},
		success: function(response) {
			console.log(response);
			showFormMessage("Confirmation message has been sent to your E-mail address.", 3000);
			hideDataLoader();
		}
	});
	
	
	
}

function showDataLoader() {
	$("#data-loader").fadeIn(100);
}

function hideDataLoader() {
	$("#data-loader").fadeOut(100);
}

var formMessageTimeout = null;
function showFormMessage(message, length) {
	var messageHolder = document.getElementById("form-message");
	messageHolder.innerHTML = message;
	
		$(messageHolder).fadeIn(250);
	
	clearTimeout(formMessageTimeout);
	formMessageTimeout = setTimeout(function() {
		$(messageHolder).fadeOut(250);
		formMessageTimeout = null;
	}, length);
	
}

var isConfirmationWindowActive = false;
function toggleConfirmationMessageWindow() {
	var confirmWindow = document.getElementById("confirmation-message-holder");
	
	if (!isConfirmationWindowActive) {
		$(confirmWindow).fadeIn(500);
		isConfirmationWindowActive = true;
	}
	
	else {
		$(confirmWindow).fadeOut(500);
		isConfirmationWindowActive = false;
	}
	
}






















