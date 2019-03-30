var admins = null;
var controllerPath = "/sysadmin";

window.onload = function() {
	
	//get all available admins and add them to the select tag
	/*
	axios.get(controllerPath + '/getRegisteredUsers')
		.then(response => {
			
			let admins = response.data;
			
			for(let i = 0; i < admins.length; i++) {
				
				var select = document.getElementById("company-admin");
				var option = document.createElement("option");
				option.text = admins[i].username + " " + admins[i].firstName + " " + admins[i].lastName;
				select.add(option);
			}
			
		}); */
	
	addNavBarListeners();
	
}


function addNavBarListeners() {
	
	//navbar left selections listeners
	$('li#register-company-selection').click(function() {
		$(".selection-component").hide(); //hide all other components
		$("#register-company-table").toggle(); //toggle the one's under the clicked selection
	});
	
	$('li#admins-selection').click(function() {
		$(".selection-component").hide();
		$("#register-admin-table").toggle();
	});
	
	//navbar right selections listeners
	//TODO: implement
	
}

function registerCompany() {
	
	//validate form
	if(validateInputFields("#register-company-table") === false) {
		alert("Empty field detected");
		return;
	}
	
	alert("Implement");
	
	/*
	axios.post(controllerPath + "/registerCompany", getCompanyJson())
		.then(response => alert(response.data));*/
}


function getCompanyJson() {
	
	var tokens = $("#company-admin :selected").text().split(" ");
	
	return {
		"name": $("#company-name").val(),
		"type":  $("#company-type :selected").val(),
		"adminUsername": tokens[0]
	}; 
}


function registerAdmin() {
	
	//check whether there is an empty input field
	if(validateInputFields("#register-admin-table") === false) {
		toast("Empty field detected");
		return;
	}
	
	//check if the password and confirm password match
	if( $("#admin-password").val().localeCompare($("#admin-confirm-password").val()) != 0 ) {
		toast("Passwords don't match");
		return;
	}
	
	//sending data to server
	axios.post(controllerPath + "/registerAdmin", getAdminJson())
		.then(response => {
			
			if(response.data === null || response.data === "") {
				toast("Registration failed");
				return;
			}
			
			toast("Admin successfully registered");
		}); 
	
	
}


function getAdminJson() {
	
	return {
		"username": $("#admin-username").val(),
		"password": $("#admin-password").val(),
		"firstName": $("#admin-first-name").val(),
		"lastName": $("#admin-last-name").val(),
		"email": $("#admin-email").val(),
		"companyType": $("#admin-company").val()
	};
}


function validateInputFields(tableID) {
	
	var valid = true;
	$(tableID + " :input").not(":button").each(function() {
		
		var input = $(this).val();
		
		if(input === "" || input === null) {
			valid = false;
		}
		
	});
	
	return valid;
}