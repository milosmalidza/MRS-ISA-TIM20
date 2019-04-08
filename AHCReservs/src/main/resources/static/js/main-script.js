
//Use this for session info
var sessionUser = null;

window.addEventListener("load", function() {
	var mainDiv = document.getElementById("main-div");
	if (mainDiv != "undefined" && mainDiv != null) {
		mainDiv.style.opacity = "1";
		mainDiv.style.webkitTransform = "scale(1)";
		mainDiv.style.transform = "scale(1)";
	}
	
	try {
		sessionUser = JSON.parse(window.localStorage.getItem("user"));
	}
	catch(err) {
		sessionUser = null;
	}
	
	if (sessionUser != null) {
		var loginButton = document.getElementById("nav-bar-login-button");
		loginButton.innerHTML = "Logout";
		
		var path = window.location.pathname;
		var page = path.split("/").pop();
		if (page == "login.html") {
			window.location.href = "index.html";
		}
	}
	
	
	console.log(sessionUser);
	
	
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
		url: "user/loginUser",
		data: {username: formData.get("username"),
			  password: formData.get("password")},
		success: function(response) {
			
			var data = JSON.parse(response);
			
			if (data.status == "success") {
				showFormMessage("Success.", 3000);
				window.localStorage.setItem("user", response);
				loadPage("index.html");
			}
			
			else if (data.status == "notEnabled") {
				showFormMessage("Account not activated.", 3000);
			}
			
			else if (data.status == "invalidUser") {
				showFormMessage("No such username.", 3000);
			}
			
			else if (data.status == "invalidPassword") {
				showFormMessage("Invalid password.", 3000);
			}
			
			console.log(response);
			hideDataLoader();
		}
	})
	
}

var isPageLoading = false;
function loadPage(url) {
	
	
	var animationDiv = document.getElementById("exiting-page-animation");
	
	
	
	if (animationDiv == null) {
		if (url == "login.html" && sessionUser != null) {
			logout(url);
		}
		
		window.location.href = url;
		return;
	}
	
	
	animationDiv.style.width = "100%";
	
	if (!isPageLoading) {
		isPageLoading = true;
		setTimeout(function() {
			if (url == "login.html" && sessionUser != null) {
				logout(url);
				return;
			}
			window.location.href = url;
		}, 700);
	}
	
}

function logout(url) {
	window.localStorage.setItem("user", "");
	sessionUser = null
	
	if (url != null) {
		if (url == "login.html") {
			window.location.href = "index.html";
		}
		else {
			window.location.href = url;
		}
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
	var jsonData = {
		username : formData.get("username"),
		password : formData.get("password"),
		firstName : formData.get("first-name"),
		lastName : formData.get("last-name"),
		email : formData.get("email")
	};
	
	$.ajax({
		type: "POST",
		url: "user/registerUser",
		data: {json : JSON.stringify(jsonData)},
		success: function(response) {
			
			if (response == "success") {
				showFormMessage("Confirmation message has been sent to your E-mail address.", 3000);
			}
			else if (response == "emailExists"){
				document.getElementById("email").parentNode.classList.add("error");
				showFormMessage("That E-mail address already exists.", 3000);
			}
			else if (response == "usernameExists") {
				document.getElementById("username").parentNode.classList.add("error");
				showFormMessage("That Username address already exists.", 3000);
			}
			
			else if (response == "emailError") {
				document.getElementById("email").parentNode.classList.add("error");
				showFormMessage("Invalid E-mail address.", 3000);
			}
			
			
			console.log(response);
			
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






















