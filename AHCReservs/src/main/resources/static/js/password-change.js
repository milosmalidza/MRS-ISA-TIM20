var controllerPath = "/user";


window.onload = function() {
	
	let user = window.localStorage.getItem("user");
	
	if(user === "") {
		history.back();
		return;
	}
	
}


function changePassword() {
	
	if(validateInputFields("#password-table") === false) {
		toast("Empty field detected");
		return;
	}
	
	if($("#password").val() != $("#confirm-password").val()) {
		toast("Passwords do not match");
		return;
	}
	
	
	let user = JSON.parse(window.localStorage.getItem("user"));
	
	axios.post(controllerPath + "/changePassword", getUserJson(user))
	.then(response => {
		
		if(response.data === "" || response.data === null) {
			toast("You must use a different password");
		} else {
			
			//add the user with new data to session
			response.data.user = user.user;
			response.data.status = user.status;
			window.localStorage.setItem("user", JSON.stringify(response.data));
			
			redirectAdmin(response.data);
		}
		
	});
	
}


function getUserJson(user) {
	
	return {
		"id": user.id,
		"password": $("#password").val(),
		"userType": user.user
	};
	
}


function redirectAdmin(admin) {
	
	switch(admin.user) {
	
	case "hotelAdmin":
		window.location.href = "hotel-profile.html";
		return;
		
	case "sysAdmin":
		window.location.href = "register-company.html";
		return;
		
	case "airAdmin":
		window.location.href = "index.html";
		return;
		
	case "carAdmin":
		//TODO: redirekcija rent a car admin-a
		window.location.href = "index.html";
		return;
	
	}
	
	
}