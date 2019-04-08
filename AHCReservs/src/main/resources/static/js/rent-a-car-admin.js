

function changeCompanyInfo() {
	var companyName = document.getElementById("company-name");
	var companyDescription = document.getElementById("company-description");
	var companyAddress = document.getElementById("company-address");
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