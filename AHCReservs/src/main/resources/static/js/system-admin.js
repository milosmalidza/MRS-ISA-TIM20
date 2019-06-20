var sysAdminControllerPath = "/sysadmin";

window.onload = function() {
	
	//get the currently logged in user
	let user = window.localStorage.getItem("user");
	
	if(user === null || user === undefined || user === "") {
		window.location.href = "index.html";
	}
	
	
	let loggedUser = JSON.parse(user);
	//check the type of the user
	if(loggedUser.user != "sysAdmin" || loggedUser.passwordChanged === false) {
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
	
	/*
	//check if the company exists
	let exists = await this.companyExists(companyName);
	if(exists === false) {
		toast("Company does not exist");
		return;
	} */
	
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
		"companyId": $("#company-name :selected").val(),
		"type":  $("#company-type-form2 :selected").val(),
		"usernames": admins
	}; 
	
}
		
/*
async function companyExists(companyName) {
	
	var response = await this.axiosCompanyExists(companyName);
	return response.data;
		
}

async function axiosCompanyExists(companyName) {
	
	let companyNameJson = {"companyName": companyName };
	return axios.post(sysAdminControllerPath + "/companyExists", companyNameJson)

}*/


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
	
	if($("#admin-password").val().length < 6) {
		toast("Password must be at least 6 characters long");
		return;
	}
	
	//check whether email is valid
	if(validateEmail($("#admin-email").val()) === false) {
		toast("Invalid email");
		return;
	}
	
	showDataLoader();
	
	//sending data to server
	axios.post(sysAdminControllerPath + "/registerAdmin", getAdminJson())
		.then(response => {
			
			toast(response.data);
			
			//clear the input fields if the registration was successful
			if(response.data.toLowerCase().includes("confirmation")) {
				clearInputFields("#register-admin-table");
			}
			
			 
			hideDataLoader();
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


function getCompanyNames(companyTypeId) {
	
	clearSemanticSelect("company-names-holder", "company-name", "Select company");
	
	getCompanies($(companyTypeId).val());
	
}

function getCompanies(companyType) {
	
	switch(companyType) {
	
	case "hotel":
		getCompaniesFromServer("getHotels", companyType);
		break;
		
	case "rent-a-car":
		getCompaniesFromServer("getRentACarServices", companyType);
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
	
	
	$(".initially-hidden-element").hide();
	$("#available-rooms-table").hide();
	$("#available-cars-table").hide();
	$("#additional-services-table").hide();
	
	//memorize the id of the selected company
	$("#main-div").attr("data-company-id", $("#company-name").val());
	
	$(".initially-hidden-element.common-row").show(); //display date picker
	
	if ($("#company-type").val() == "hotel") {
		$(".initially-hidden-element.hotel-row").show();
	}
	else if ($("#company-type").val() == "rent-a-car") {
		
		$.ajax({
			type : "post",
			url : sysAdminControllerPath + "/getRentACarServiceBranches",
			data : {json : JSON.stringify({
				serviceId : $("#main-div").attr("data-company-id")
			})},
			success : function(response) {
				
				var data = JSON.parse(response);
				var values = [];
				for (var i = 0; i < data.length; i++) {
					values.push({
						id : data[i].id,
						value : data[i].name + ", " + data[i].address
					});
				}
				
				addValuesAndIDsToSelect("#start-destination", values, null);
				addValuesAndIDsToSelect("#end-destination", values, null);
				
				$(".initially-hidden-element.car-row").show();
			}
		})
		
		
	}
	
}

function listServiceVehicles(vehicles) {
	var table = document.getElementById("available-cars-table");
	console.log(vehicles);
	var content = "<tr>" +
		"<td>Name</td>" +
		"<td>Doors</td>" +
		"<td>Max people</td>" + 
		"<td>Price per day</td>" + 
		"<td>Vehicle type</td>" +
		"<td>Check</td>" +
	"</tr>";
	
	vehicles.forEach(function(item, index) {
		content += "<tr>" +
			"<td>" + item.name + "</td>" +
			"<td>" + item.numOfDoors + "</td>" +
			"<td>" + item.numOfSeats + "</td>" + 
			"<td>" + item.pricePerDay +"</td>" + 
			"<td>" + item.vehicleType + "</td>" +
			"<td><input class='vehicle-checkbox' type='checkbox' data-vehicle-id='" + item.id + "' /></td>" +
		"</tr>";
	});
	
	content += "<tr><td colspan='6'><input type='button' class='ui positive button'" +
				"value='Make reservation' style='width: 400px !important;' onclick='makeVehicleReservations()'/></td></tr>";
	
	table.innerHTML = content;
	$("#available-cars-table").show();
	
}
		
function makeVehicleReservations() {
	var checkboxes = document.getElementsByClassName("vehicle-checkbox");
	console.log(checkboxes);
	var startLocation = document.getElementById("start-destination");
	var endLocation = document.getElementById("end-destination");
	var checked = []
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i].checked) {
			checked.push({
				vehicleId : checkboxes[i].getAttribute("data-vehicle-id"),
				startDate : roomCheckInDate,
				endDate : roomCheckOutDate,
				startLocation : startLocation.options[startLocation.selectedIndex].innerHTML,
				endLocation : endLocation.options[endLocation.selectedIndex].innerHTML
			});
		}
	}
	
	if (checked.length == 0) return;
	
	var json = {
		serviceId : $("#main-div").attr("data-company-id"),
		checked : checked
	};
	
	$.ajax({
		type : "post",
		url : sysAdminControllerPath + "/makeVehicleReservations",
		data : {json : JSON.stringify(json),
			   user : JSON.stringify(sessionUser)},
		success : function(response) {
			console.log(response);
			for (var i = 0; i < checkboxes.length; i++) {
				if (checkboxes[i].checked) {
					checkboxes[i].checked = false;
					$(checkboxes[i].parentNode.parentNode).hide();
				}
			}
			if (response == "success") {
				notify("Success", "Successfully made quick reservations");
				
			}
			else {
				try {
					var data = JSON.parse(response);
					var t = "";
					
					for (var i = 0; i < data.length; i++) {
						if (i < data.length - 1) {
							t += data[i].name + ", ";
						}
						else {
							t += data[i].name;
						}
						
					}
					
					notify("Partial success", "Coludn't make reservations for this vehicles: " + t);
					
					
				}
				catch(e) {
					notify("Something went wrong", "Please login again")
				}
			}
		}
	})
	
	
	console.log(checked);
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
		
