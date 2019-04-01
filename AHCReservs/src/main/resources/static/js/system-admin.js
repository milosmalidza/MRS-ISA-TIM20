var controllerPath = "/sysadmin";


/* Company registration functions */

function registerCompany() {
	
	//validate form
	if(validateInputFields("#register-company-table") === false) {
		alert("Empty field detected");
		return;
	}
	
	//send data to server
	axios.post(controllerPath + "/registerCompany", getCompanyJson())
		.then(response => {
			
			if(response.data === null || response.data === "") {
				toast("Registration failed");
				return;
			}
			
			toast("Company successfully registered");
			
			//the selected admin is no longer available
			getAdmins();
			
		});
	
}

 
function getAdmins() {
	
	//clear the select tag for admins
	//$("#company-admins").val("");
	
	var n = document.getElementById("nesto");
	var texts = n.getElementsByClassName("text");
	
	texts[0].classList.add("default");
	texts[0].innerHTML = "Select admin";
	
	
	$('#company-admins option:selected').remove();
	$('#company-admins').empty().append('<option value="">Select admin</option>');
	
	
	let companyType = $("#company-type").val();
	
	switch(companyType) {
		
	case "airline":
		//getAdminsFromServer("/getAvailableAirlineAdmins");
		break;
	case "hotel":
		getAdminsFromServer("/getAvailableHotelAdmins");
		break;
		
	case "rent-a-car":
		//getAdminsFromServer("/getAvailableRentACarAdmins");
		break;
	}
	
}


function getAdminsFromServer(serverMethodPath) {
	
	//get the admins for the selected company from the server
	axios.get(controllerPath + serverMethodPath)
		.then(response => {
			
			let admins = response.data;
			let select = document.getElementById("company-admins");
			
			//if there aren't any available admins we can't register a company
			if(admins.length === 0) {
				toast("No admins available for the selected company");
				return;
			}
			
			//insert admins as options in the select tag
			for(let i = 0; i < admins.length; i++) {
				let option = document.createElement("option");
				option.text = admins[i].username + " " + admins[i].firstName + " " + admins[i].lastName;
				option.value = admins[i].username;
				select.add(option);
			}
			
		});
}


function getCompanyJson() {
	
	var tokens = $("#company-admins :selected").text().split(" ");
	
	return {
		"name": $("#company-name").val(),
		"type":  $("#company-type :selected").val(),
		"adminUsername": tokens[0]
	}; 
}


/* Admin registration functions */

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
		"companyType": $("#admin-type").val()
	};
}


/* Function for validating input fields by checking whether they're empty */

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