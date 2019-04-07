var controllerPath = "/hotelAdmin";

window.onload = function() {
	
	//TODO: get the hotel of the currently logged in admin
	
}

function saveChanges() {
	
	if(validateInputFields("#hotel-profile-table") === false) {
		toast("Empty fields detected");
		return;
	}
	
	toast("TODO: Implement");
	
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