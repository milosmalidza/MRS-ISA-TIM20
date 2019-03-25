var controllerPath = "/hotelAdmin";

window.onload = function() {
	
	//** DUMMY DATA **//
	//get a hotel from company list
	
	axios.get(controllerPath + "/getCompany")
	.then(response => {
		
		if(response != null && response != undefined) {
			
			$("#hotel-name").val(response.data.name);
			
		}
		
	});
}

function saveChanges() {
	
	if(validateInputFields("#hotel-profile-table") === false) {
		toast("Empty fields detected");
		return;
	}
	
	toast("Changes saved");
	
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