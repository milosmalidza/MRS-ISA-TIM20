var controllerPath = "/hotelAdmin";

window.onload = function() {
	
	//** DUMMY DATA **//
	//get the hotel of the currently logged in admin
	
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