var controllerPath = "/user";

window.onload = function() {
	
	let user = window.localStorage.getItem("user");
	let parsedUser;
	
	if(user === "") {
		history.back();
		return;
	}
	
	parsedUser = JSON.parse(user);
	
	if(parsedUser.passwordChanged === false) {
		history.back();
		return;
	}
	
	fillUserInputs(parsedUser);
}

function fillUserInputs(user) {
	
	console.log(user);
	
	$("#username").val(user.username);
	$("#password").val(user.password);
	$("#first-name").val(user.firstName);
	$("#last-name").val(user.lastName);
	$("#email").val(user.emailId);
	
}


function updateProfile() {
	
	if(validateInputFields("#user-data-table") === false) {
		toast("Empty field detected");
		return;
	}
	
	if($("#password").val() != $("#confirm-password").val()) {
		toast("Passwords don't match");
		return;
	}
	
	//check whether email is valid
	if(validateEmail($("#email").val()) === false) {
		toast("Invalid email");
		return;
	}
	
	var user = JSON.parse(window.localStorage.getItem("user"));
	
	
	axios.post(controllerPath + "/updateProfile", getUserJson(user))
		.then(response => {
			
			if(response.data != "") {
				
				//add the user with new data to session
				response.data.user = user.user;
				response.data.status = user.status;
				window.localStorage.setItem("user", JSON.stringify(response.data));
				toast("Profile updated");
			} else {
				toast("Username already exists");
			}
			
		});
	
}

function getUserJson(user) {
	
	return {
		"id": user.id,
		"username":$("#username").val(),
		"password": $("#password").val(),
		"firstName": $("#first-name").val(),
		"lastName": $("#last-name").val(),
		"email": $("#email").val(),
		"userType": user.user
	};	
	
}