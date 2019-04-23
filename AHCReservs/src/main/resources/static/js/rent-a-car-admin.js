
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
		nemDescription : companyDescription.value,
		newAddress : companyAddress.value
	};
	
	$.ajax({
		type: "post",
		url: "rentACarService/EditService",
		data: {json : JSON.stringify(json)},
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



















































