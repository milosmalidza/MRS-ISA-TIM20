
//Use this for session info
var sessionUser = null;

window.addEventListener("load", function() {
	
	
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
		if (sessionUser.user == "carAdmin") {
			if (page == "" || page == "index.html") {
				loadPage("edit-rent-a-car-service.html?username=" + sessionUser.username + "&password=" + sessionUser.password);
				return;
			}
		}
		
		if (page == "login.html") {
			window.location.href = "index.html";
		}
	}
	
	console.log(sessionUser);
	
	var mainDiv = document.getElementById("main-div");
	if (mainDiv != "undefined" && mainDiv != null) {
		mainDiv.style.opacity = "1";
		mainDiv.style.webkitTransform = "scale(1)";
		mainDiv.style.transform = "scale(1)";
	}
	
	
}, false);




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
			console.log(response);
			var data = JSON.parse(response);
			
			if (data.status == "success") {
				showFormMessage("Success.", 3000);
				window.localStorage.setItem("user", response);
				
				if(data.user != "registeredUser" && data.passwordChanged === false) {
					loadPage("password-change.html");
				}
				
				else if (data.user == "sysAdmin") {
					loadPage("register-company.html");
				}
				
				else if (data.user == "airAdmin") {
					loadPage("/");
				}
				
				else if (data.user == "hotelAdmin") {
					loadPage("hotel-profile.html");
				}
				
				else if (data.user == "carAdmin") {
					loadPage("edit-rent-a-car-service.html?username=" + data.username + "&password=" + data.password);
				}
				
				else {
					loadPage("/");
				}
				
				
			}
			
			else if (data.status == "notAssigned") {
				showFormMessage("No company has been assigned to you.", 3000);
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
			
			
			hideDataLoader();
		}
	})
	
}

var isPageLoading = false;
function loadPage(url) {
	
	//if the user is not logged in don't redirect him to the user profile
	if(url == 'user-profile.html' && sessionUser == null) {
		notify("User profile", "Please login in order to proceed");
		return;
	}
	
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
	
	if(pass.value.length < 6) {
		pass.parentNode.classList.add("error");
		showFormMessage("Password must be at least 6 characters long.", 3000);
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

var confirmationSelectedElement = null
var isConfirmationWindowActive = false;
function toggleConfirmationMessageWindow(element) {
	
	if (element.id == "toggle-yes") {
		removeDataElement(confirmationSelectedElement);
	}
	
	
	var confirmWindow = document.getElementById("confirmation-message-holder");
	confirmationSelectedElement = element;
	
	if (!isConfirmationWindowActive) {
		$(confirmWindow).fadeIn(500);
		isConfirmationWindowActive = true;
	}
	
	else {
		$(confirmWindow).fadeOut(500);
		isConfirmationWindowActive = false;
	}
	
}

function removeDataElement(element) {
	
	var dataType = element.getAttribute("data-type");
	
	if (dataType == "vehicle") {
		var closest = element.closest(".view-vehicle-item");
		
		var data = {
			vehicle : closest.getAttribute("data-vehicle")
		};
		
		$.ajax({
			type: "post",
			url: "rentACarService/RemoveCar",
			data: {json : JSON.stringify(data),
					user : JSON.stringify(sessionUser)
				
				},
			success: function(response) {
				console.log(response);
				if (response == "success") {
					$(closest).css("display", "none");
				}
				else if (response == "reserved") {
					alert("Vehicle is already reserved and cannot be removed!");
				}
				
			}
		})
	}
	
	
}





function mouseEnterCompanyItem(element) {
	element.style.color = "white";
	element.style.borderTopColor = "rgba(38,102,71,1.00)";
	var animationDiv = element.getElementsByClassName("company-item-animation-layer")[0];
	animationDiv.style.background = "rgba(38,102,71,1.00)";
	
}

function mouseLeaveCompanyItem(element) {
	element.style.color = "black";
	element.style.borderTopColor = "black";
	var animationDiv = element.getElementsByClassName("company-item-animation-layer")[0];
	animationDiv.style.background = "transparent";
}


var notificationTimeout = null;
function notify(title, content) {
	
	var notificationWindow = document.getElementById("notification-window");
	var notificationTitle = document.getElementById("notification-title");
	var notificationContent = document.getElementById("notification-content");
	
	notificationTitle.innerHTML = title;
	notificationContent.innerHTML = content;
	
	notificationWindow.style.right = "0px";
	clearTimeout(notificationTimeout);
	
	notificationTimeout = setTimeout(function(){
		notificationWindow.style.right = "-400px";
	}, 4000);
	
	
}







































