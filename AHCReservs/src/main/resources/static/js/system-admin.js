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
			
			toast(response.data); //print out the response to the user
			getAdmins(); //the selected admin is no longer available
			
		});
	
}

 
async function getAdmins() {

	
	clearDisplayedAdmins("company-admins-holder", "company-admins");
	
	/* Get admins */
	let companyType = $("#company-type-form1").val();
	let admins = await getCompanyAdmins(companyType);
	
	
	//if there aren't any available admins we can't register a company
	if(admins === null || admins.length === 0) {
		toast("No admins available for the selected company");
		return;
	}
	
	let select = document.getElementById("company-admins");
	insertAdminsToSelectTag(select, admins);
	
	
}

function clearDisplayedAdmins(tdHolder, adminsSelect) {
	
	/* Clearing select tag for admins */
	var n = document.getElementById(tdHolder);
	var texts = n.getElementsByClassName("text");
	
	texts[0].classList.add("default");
	texts[0].innerHTML = "Select admin";
	
	$('#' + adminsSelect + 'option:selected').remove();
	$('#' + adminsSelect).empty().append('<option value="">Select admin</option>');
	
}

function insertAdminsToSelectTag(select, admins) {
	
	//insert admins as options in the select tag
	for(let i = 0; i < admins.length; i++) {
		let option = document.createElement("option");
		option.text = admins[i].username + " " + admins[i].firstName + " " + admins[i].lastName;
		option.value = admins[i].username;
		select.add(option);
	}
	
}

async function getCompanyAdmins(companyType) {
	
	switch(companyType) {
	
	case "airline":
		return await getAdminsFromServer("/getAvailableAirlineAdmins");
		
	case "hotel":
		return await  getAdminsFromServer("/getAvailableHotelAdmins");
		
	case "rent-a-car":
		return await  getAdminsFromServer("/getAvailableRentACarAdmins");
	}
	
}


async function getAdminsFromServer(serverMethodPath) {
	
	var response = await this.axiosAdmins(serverMethodPath);
	console.log(response.data);
	return response.data;
}

async function axiosAdmins(serverMethodPath) {
	return await axios.get(controllerPath + serverMethodPath);
}


async function viewAdmins() {
	
	//clearDisplayedAdmins
	var multiSelect = document.getElementById("available-admins");
	multiSelect.selectedIndex = -1;
	$('#available-admins option:selected').remove();
	$('#available-admins').empty().append('<option value="">Select admin</option>');
	
	/* Get admins */
	let companyType = $("#company-type-form2").val();
	let admins = await getCompanyAdmins(companyType);
	
	
	//if there aren't any available admins we can't register a company
	if(admins === null || admins.length === 0) {
		toast("No admins available for the selected company");
		return;
	}
	
	let select = document.getElementById("available-admins");
	insertAdminsToSelectTag(select, admins);
	
	
}

async function addAdmins() {
	
	let companyName = $("#admins-company-name").val();
	
	//check if the company exists
	let exists = await this.companyExists(companyName);
	if(exists === false) {
		toast("Company does not exist");
		return;
	} 
	
	
	//alert(JSON.stringify(getCompanyAdminsJson()));
	
	//sending data to server
	
	axios.post(controllerPath + "/addAdminsToCompany", getCompanyAdminsJson())
		.then(response => {
			
			toast(response.data); //print out the response to the user
			viewAdmins();
			
		}); 
	
}


function getCompanyAdminsJson() {
	
	var admins = $("#available-admins").val();
	
	return {
		"name": $("#admins-company-name").val(),
		"type":  $("#company-type-form2 :selected").val(),
		"usernames": admins
	}; 
	
}
		

async function companyExists(companyName) {
	
	var response = await this.axiosCompanyExists(companyName);
	return response.data;
		
}

async function axiosCompanyExists(companyName) {
	
	let companyNameJson = {"companyName": companyName };
	return axios.post(controllerPath + "/companyExists", companyNameJson)

}


function getCompanyJson() {
	
	var tokens = $("#company-admins :selected").text().split(" ");
	
	return {
		"name": $("#company-name").val(),
		"type":  $("#company-type-form1 :selected").val(),
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
	
	//check whether email is valid
	if(validateEmail($("#admin-email") === false)) {
		toast("Invalid email");
		return;
	}
	
	//check whether email is valid
	
	//sending data to server
	axios.post(controllerPath + "/registerAdmin", getAdminJson())
		.then(response => (toast(response.data))); 

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

function validateEmail(elementValue){      
	   var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	   return emailPattern.test(elementValue); 
	 } 