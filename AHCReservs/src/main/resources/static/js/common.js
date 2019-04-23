
/* Function for validating input fields by checking whether they're empty 
 * @args: tableID - # + id of the table where the inputs are located */
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

//clears all input fields except select
function clearInputFields(tableID) {
	
	$(tableID + " :input").not(":button").each(function() {
		
		if(this.type.includes('select')) {
			return;
		}
		
		$(this).val(''); //clear input
		
	});
}

/* Completely cleares the semantic select tag
   tdHolder - id of the td containing the select (id without '#')
   selectID - id of the select (id without '#')
   defaultText - default text inserted into the select */
function clearSemanticSelect(tdHolder, selectID, defaultText) {
	
	/* Clearing select tag for admins */
	var n = document.getElementById(tdHolder);
	var texts = n.getElementsByClassName("text");
	
	texts[0].classList.add("default");
	texts[0].innerHTML = defaultText;
	
	$('#' + selectID + 'option:selected').remove();
	$('#' + selectID).empty().append('<option value="">Select admin</option>');
	
}

/* Completely cleares the semantic multi select tag
selectID - id of the select (id WITH #) 
defaultText - default text inserted into the select */
function clearSemanticMultiSelect(selectID, defaultText) {
	
	$(selectID).dropdown('clear');
	var multiSelect = document.getElementById(selectID.substring(1));
	multiSelect.selectedIndex = -1;
	$(selectID + ' option:selected').remove();
	$(selectID).empty().append('<option value="">' + defaultText + '</option>');
	
}


function validateEmail(elementValue){      
	   var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	   return emailPattern.test(elementValue); 
} 