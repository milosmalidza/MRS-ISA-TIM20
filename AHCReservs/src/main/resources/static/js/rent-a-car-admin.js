
var companyMessageTimeout = null;
function showCompanyMessage(message, length) {
	var messageHolder = document.getElementById("form-message-1");
	messageHolder.innerHTML = message;
	
		$(messageHolder).fadeIn(250);
	
	clearTimeout(companyMessageTimeout);
	companyMessageTimeout = setTimeout(function() {
		$(messageHolder).fadeOut(250);
		companyMessageTimeout = null;
	}, length);
	
}


function changeCompanyInfo () {
	var companyName = document.getElementById("company-name");
	var companyDescription = document.getElementById("company-description");
	var companyAddress = document.getElementById("company-address");
	
	var json = {
		newName : companyName.value,
		newDescription : companyDescription.value,
		newAddress : companyAddress.value
	};
	
	$.ajax({
		type: "post",
		url: "rentACarService/EditService",
		data: {json : JSON.stringify(json),
			  user : JSON.stringify(sessionUser)},
		success: function (response) {
			if (response == "success") {
				showCompanyMessage("Successfully changed company information.", 3000);
			}
			else if (response == "badRequest") {
				showCompanyMessage("Bad request, try reloading the page.", 3000);
			}
			
			else if (response == "notLoggedIn") {
				showCompanyMessage("Bad request, try logging in first.", 3000);
			}
			
			else if (response == "exists") {
				showCompanyMessage("Company name already exists, please try another.", 3000);
			}
			
			
			
		}
	});
	
	
}



function addRentACarServiceCar() {
	showDataLoader();
	var form = document.getElementById("single-form");
	var inputs = form.getElementsByTagName("input");
	
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
	var numberOfSeatsSelect = document.getElementById("number-of-doors");
	var typeOfVehicle = document.getElementById("type-of-vehicle");
	
	if (numberOfSeatsSelect.value == "") {
		emptyField = true;
		numberOfSeatsSelect.parentNode.classList.add("error");
	}
	else {
		numberOfSeatsSelect.parentNode.classList.remove("error");
	}
	
	if (typeOfVehicle.value == "") {
		emptyField = true;
		typeOfVehicle.parentNode.classList.add("error");
	}
	else {
		typeOfVehicle.parentNode.classList.remove("error");
	}
	
	
	
	if (emptyField) {
		showFormMessage("Please fill the required fields and proceed.", 3000);
		hideDataLoader();
		return;
	}
	
	
	var data = new FormData(form);
	
	var json = {
		name : data.get("vehicle-name"),
		description : data.get("description"),
		numberOfSeats : data.get("number-of-seats"),
		numberOfDoors : data.get("number-of-doors"),
		typeOfVehicle : data.get("type-of-vehicle"),
		pricePerDay : data.get("price-per-day")
	};
	
	$.ajax({
		type: "post",
		url: "rentACarService/AddCar",
		data: {json : JSON.stringify(json)},
		success: function(response) {
			
			if (response == "success") {
				showFormMessage("Car has been successfully added.", 3000);
			}
			else if (response == "badNumber") {
				showFormMessage("Make sure that you have entered whole number.", 3000);
			}
			else if (response == "badType") {
				showFormMessage("Bad vehicle type.", 3000);
			}
			else if (response == "badRequest") {
			 	showFormMessage("Bad request.", 3000);
		 	}
			
			
			console.log(response);
			
			//showFormMessage("Car has been successfully added.", 3000);
			hideDataLoader();
		}
	});
	
}




var vehicleReservationStartDate = null;
var vehicleReservationEndDate = null;


function searchVehicles() {
	showDataLoader();
	var form = document.getElementById("vehicle-search-form");
	var itemsHolder = document.getElementById("items-holder");
	var mainDiv = document.getElementById("main-div");
	
	
	
	var dateElement = document.getElementById("daterange");
	var doorsElement = document.getElementById("number-of-doors");
	var peopleElement = document.getElementById("number-of-people");
	var typeElement = document.getElementById("type-of-vehicle");
	
	var is_error = false;
	
	dateElement.classList.remove("error");
	doorsElement.classList.remove("error");
	peopleElement.parentNode.classList.remove("error");
	typeElement.classList.remove("error");
	
	if (vehicleReservationStartDate == null || vehicleReservationEndDate == null) {
		dateElement.classList.add("error");
		showFormMessage("Please select reservation date.", 3000);
		hideDataLoader();
		return;
	}
	
	if (dateElement.value == "") {
		dateElement.classList.add("error");
		showFormMessage("Please select reservation date.", 3000);
		hideDataLoader();
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
        url: "rentACarService/searchVehicles",
        data:{json : JSON.stringify(
			{
				companyid : mainDiv.getAttribute("data-id"),
				startDate:  vehicleReservationStartDate,
			 	endDate : vehicleReservationEndDate,
			 	doors : parseInt(doorsElement.value),
			 	people : parseInt(peopleElement.value),
			 	type : typeElement.value
			})},
        success:function(response){
        	console.log(response);
            var cars = JSON.parse(response);
			if (cars.length != 0) {
				var vehicleItemsRow = document.getElementById("vehicle-items-row");
				vehicleItemsRow.innerHTML = "";
				
				var row = document.createElement("div");
				row.setAttribute("class", "row");
				
				for (var i = 0; i < cars.length; i++) {
					var itemHolder = document.createElement("div");
					itemHolder.setAttribute("class", "col-lg-3  col-sm-4 col-xs-6 vehicle-item");
					itemHolder.innerHTML =  '<div class="vehicle-item-holder" data-id="'+ cars[i].id +'" >' +
												'<div class="vehicle-item-name">' + cars[i].name + '</div>' +
												'<div class="vehicle-item-description">' + cars[i].description + '</div>' +
												'<div class="vehicle-item-number-of-people bold-font">Number of people: ' + cars[i].numOfSeats + '</div>' +
												'<div class="vehicle-item-number-of-doors bold-font">Number of doors: ' + cars[i].numOfDoors + '</div>' +
												'<div class="vehicle-item-vehicle-type bold-font">Type: ' + cars[i].vehicleType + '</div>' +
												'<div class="vehicle-item-price-per-day bold-font">Price per day: ' + cars[i].pricePerDay + '</div>' +
												'<input class="ui button reserve-vehicle-button" type="button" value="Reserve" onclick="performReservation(this)"/>' + 
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


function performReservation(element) {
	
	var parentNode = element.parentNode;
	
	var id = {
		id : parentNode.dataset.id,
		startDate:  vehicleReservationStartDate,
		endDate : vehicleReservationEndDate
	};
	
	console.log(id);
	
	$.ajax({
		type : "POST",
		url : "rentACarService/reserveVehicle",
		data : {json : JSON.stringify(id), user : JSON.stringify(sessionUser)},
		success : function(response) {
			
			if (response == "success") {
				closeSearchVehicles();
				showFormMessage("Vehicle has been successfully reserved.", 3000);
			}
			else if (response == "notLoggedIn") {
				showReservationMessage("You are not logged in.", 3000);
			}
			else if (response == "badRequest") {
				showReservationMessage("Bad request.", 3000);
			}
			
			
			console.log(response);
		}
	})
	
}



function closeSearchVehicles() {
	var itemsHolder = document.getElementById("items-holder");
	document.body.style.overflowY = "auto";
	itemsHolder.style.left = "100%";
}


function showReservationMessage(message, length) {
	var messageHolder = document.getElementById("message-items-holder");
	messageHolder.innerHTML = message;
	
		$(messageHolder).fadeIn(250);
	
	clearTimeout(formMessageTimeout);
	formMessageTimeout = setTimeout(function() {
		$(messageHolder).fadeOut(250);
		formMessageTimeout = null;
	}, length);
	
}

function viewVehicles() {
	loadPage("view-vehicles.html?username=" + sessionUser.username + "&password=" + sessionUser.password);
}

function saveEdit() {
	
	
	var json = {
		id : vehicleEditId,
		name : $("#edit-vehicle-name").val(),
		description : $("#edit-vehicle-description").val(),
		type : $('#edit-type-of-vehicle').val(),
		doors : $('#edit-vehicle-doors').val(),
		people : $("#edit-vehicle-capacity").val(),
		price : $("#edit-vehicle-price").val()
	};
	
	var user = sessionUser;
	
	$.ajax({
		type : "post",
		url : "rentACarService/saveEditedVehicle",
		data : {json : JSON.stringify(json),
			   user : JSON.stringify(user)},
		success : function(response) {
			console.log(response);
			if (response == "success") {
				
				selectedEditVehicle.getElementsByClassName("view-vehicle-name")[0].innerHTML = json.name;
				selectedEditVehicle.getElementsByClassName("view-vehicle-description")[0].innerHTML = json.description;
				selectedEditVehicle.getElementsByClassName("view-vehicle-type")[0].innerHTML = 'Vehicle type: <span style="font-weight: bold;">' + json.type + '</span>';
				selectedEditVehicle.getElementsByClassName("view-vehicle-doors")[0].innerHTML = 'Vehicle doors: <span style="font-weight: bold;">' +json.doors+ '</span>';
				selectedEditVehicle.getElementsByClassName("view-vehicle-people")[0].innerHTML = 'Vehicle seats: <span style="font-weight: bold;">' + json.people + '</span>';
				selectedEditVehicle.getElementsByClassName("view-vehicle-price")[0].innerHTML = 'Price per day: <span style="font-weight: bold;">' + json.price + '</span>';
				
				
				closeEdit();
			}
			
		}
	});
	
	
}


function showForm(response) {
	
	console.log(response);
	
	var data = JSON.parse(response);
	
	if (data.status != "ok") {
		alert("Bad request");
		return;
	}
	
	$("#edit-vehicle-name").val(data.name);
	$("#edit-vehicle-description").val(data.description);
	$('#edit-type-of-vehicle').dropdown('set selected', data.type);
	$('#edit-vehicle-doors').dropdown('set selected', data.doors);
	$("#edit-vehicle-capacity").val(data.people);
	$("#edit-vehicle-price").val(data.price);
	
	
	openEdit();
	
}

var vehicleEditId = null;
var selectedEditVehicle = null;
function displayEditWindow(response) {
	
	console.log(response);
	
	if (response == "reserved") {
		alert("Vehicle is already reserved.");
		return;
	}
	if (response == "badRequest") {
		alert("Bad request.");
		return
	}
	
	var json = {
		id : vehicleEditId
	};
	
	$.ajax({
		type : "post",
		url : "rentACarService/GetVehicleInfo",
		data : {json : JSON.stringify(json)},
		success : showForm
		
	});
	
	
	
									  
}


function closeEdit() {
	$("#edit-vehicle-holder").fadeOut(500);
}

function openEdit() {
	$("#edit-vehicle-holder").fadeIn(500);
}




function editVehicle(element) {
	var vehicleId = parseInt(element.getAttribute("data-vehicle-id"));
	vehicleEditId = vehicleId;
	selectedEditVehicle = element.closest(".view-vehicle-item");
	console.log(selectedEditVehicle);
	checkVehicle(vehicleId, displayEditWindow);
	
	
}

function checkVehicle(id, callback) {
	
	var json = {
		id : id
	};
	
	
	$.ajax({
		type : "post",
		url : "rentACarService/CheckVehicle",
		data : {json : JSON.stringify(json)},
		success : callback
	});
	
	
	
}


var r_error = false;
function rateService(value) {
	
	if (r_error) {
		r_error = false;
		return;
	}
	
	var json = {
		id : document.getElementById("main-div").getAttribute("data-id"),
		rating : value
	};
	
	var user = sessionUser;
	if (user == null) {
		r_error = true;
		alert("Not logged in.");
		$("#service-rating").rating('clear rating');
		return;
	}
	
	$.ajax({
		type : "post",
		url : "rentACarService/rateService",
		data : {json : JSON.stringify(json),
			   user : JSON.stringify(user)},
		success : function (response) {
			console.log(response);
			if (response == "noReservation") {
				r_error = true;
				alert("You are not allowed to rate us yet.");
				$("#service-rating").rating('clear rating');
				
			}
			else if (response == "notLoggedIn") {
				r_error = true;
				alert("You are not logged in.");
				$("#service-rating").rating('clear rating');
			}
			
			else if (response == "success") {
				
			}
			
			
			
		}
	});
	
	
}








































