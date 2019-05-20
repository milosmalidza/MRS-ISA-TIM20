var sysAdminControllerPath = "/sysadmin";

window.onload = function() {
	
	//get the currently logged in user
	let user = window.localStorage.getItem("user");
	
	if(user === null || user === undefined || user === "") {
		window.location.href = "index.html";
	}
	
	
	let loggedUser = JSON.parse(user);
	//check the type of the user
	if(loggedUser.user != "sysAdmin") {
		history.back(); //redirect the user to the previous page
	}
	
}

/* Company registration functions */

function registerCompany() {
	
	//validate form
	if(validateInputFields("#register-company-table") === false) {
		alert("Empty field detected");
		return;
	}
	
	//send data to server
	axios.post(sysAdminControllerPath + "/registerCompany", getCompanyJson())
		.then(response => {
			
			toast(response.data); //print out the response to the user
			
			//if the registration was successful clear the input fields
			if(response.data.toLowerCase().includes("success")) {
				clearInputFields("#register-company-table");
				getAdmins(); //the selected admin is no longer available so I need to re-get the admins
			}
				
		});
	
}

 
async function getAdmins() {

	
	clearSemanticSelect("company-admins-holder", "company-admins", "Select admin");
	
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
	return await axios.get(sysAdminControllerPath + serverMethodPath);
}


async function viewAdmins() {

	// clear displayed admins
	clearSemanticMultiSelect("#available-admins", "Select admin");
	
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
	
	
	if(validateInputFields("#company-admins-table") === false) {
		toast("Empty field detected");
		return;
	}
	
	let companyName = $("#admins-company-name").val();
	
	//check if the company exists
	let exists = await this.companyExists(companyName);
	if(exists === false) {
		toast("Company does not exist");
		return;
	} 
	
	//sending data to server
	axios.post(sysAdminControllerPath + "/addAdminsToCompany", getCompanyAdminsJson())
		.then(response => {
			
			toast(response.data); //print out the response to the user
			viewAdmins();
			
			clearInputFields("#company-admins-table");
			
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
	return axios.post(sysAdminControllerPath + "/companyExists", companyNameJson)

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
	
	//TODO: initialize loader
	
	//sending data to server
	axios.post(sysAdminControllerPath + "/registerAdmin", getAdminJson())
		.then(response => {
			
			toast(response.data);
			
			//clear the input fields if the registration was successful
			if(response.data.toLowerCase().includes("confirmation")) {
				clearInputFields("#register-admin-table");
			}
			
			 
			//TODO: hide loader
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


function getCompanyNames() {
	
	clearSemanticSelect("company-names-holder", "company-name", "Select company");
	
	getCompanies($("#company-type").val());
	
}

function getCompanies(companyType) {
	
	switch(companyType) {
	
	case "hotel":
		getCompaniesFromServer("getHotels", companyType);
		break;
		
	case "rent-a-car":
		//getCompaniesFromServer("getRentACarServices", companyType);
		break;
		
	}
	
}

function getCompaniesFromServer(methodName, companyType) {
	
	axios.get(sysAdminControllerPath + "/" + methodName)
		.then(response => {
			
			if(response.data != "" && response.data != null) {
				
				if(response.data.length === 0) {
					toast("No" + companyType + "registered at the moment");
				} else {
					addValuesAndIDsToSelect("#company-name", getNamesAndIDsFromCompanies(response.data), "Select company");
				}
				
			}
			
		});
	
}


function getNamesAndIDsFromCompanies(companiesArray) {
	
	let companyNamesAndIDs = []
	
	for(let i = 0; i < companiesArray.length; i++) {
		companyNamesAndIDs.push( {"value": companiesArray[i].name, "id": companiesArray[i].id} );
	}
	
	return companyNamesAndIDs;
}


function companyChanged() {
	
	$("#available-rooms-table").hide();
	$("#additional-services-table").hide();
	
	//memorize the id of the selected company
	$("#main-div").attr("data-company-id", $("#company-name").val());
	
	$("tr.initially-hidden-element").show(); //display date picker
	
}


