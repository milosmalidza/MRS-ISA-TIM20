var userControllerPath = "/user"; //used to get currencies

/** Function for validating input fields by checking whether they're empty 
 *  @tableID - # + id of the table where the inputs are located */
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

/** Completely cleares the semantic select tag
   @tdHolder - id of the td containing the select (id without '#')
   @selectID - id of the select (id without '#')
   @defaultText - default text inserted into the select */
function clearSemanticSelect(tdHolder, selectID, defaultText) {
	
	/* Clearing select tag for admins */
	var n = document.getElementById(tdHolder);
	var texts = n.getElementsByClassName("text");
	
	texts[0].classList.add("default");
	texts[0].innerHTML = defaultText;
	
	$('#' + selectID + 'option:selected').remove();
	$('#' + selectID).empty().append('<option value="">' + defaultText + '</option>');
	
}

/** Completely cleares the semantic multi select tag
	@selectID - id of the select (id WITH #) 
	@defaultText - default text inserted into the select */
function clearSemanticMultiSelect(selectID, defaultText) {
	
	$(selectID).dropdown('clear');
	var multiSelect = document.getElementById(selectID.substring(1));
	multiSelect.selectedIndex = -1;
	$(selectID + ' option:selected').remove();
	$(selectID).empty().append('<option value="">' + defaultText + '</option>');
	
}


/** Initialize the select tag with values to choose
 * @selectID - id of the select tag(id with '#')
 * @listOfValues - an array containing the values to be stored in the select tag
 * @defaultText - the default text in the select tag 
 * */
function addValuesToSelect(selectID, listOfValues, defaultText) {
	
	$(selectID).dropdown('clear');
	$(selectID).empty().append('<option value="">' + defaultText + '</option>');
	
	for(let i = 0; i < listOfValues.length; i++) {
		$(selectID).append('<option value="' + listOfValues[i].toLowerCase() + '">'
				+ listOfValues[i] + '</option>');
	}
	
}

/** Used only for hotel additional services */
function addServicesToSelect(selectID, listOfValues, defaultText) {
	
	$(selectID).dropdown('clear');
	$(selectID).empty().append('<option value="">' + defaultText + '</option>');
	
	for(let i = 0; i < listOfValues.length; i++) {
		$(selectID).append('<option value="' + listOfValues[i].service.toLowerCase() + '">'
				+ listOfValues[i].service + ':  ' +  listOfValues[i].price + '</option>');
	}
	
}

function addValuesAndIDsToSelect(selectID, listOfValues, defaultText) {
	
	$(selectID).dropdown('clear');
	$(selectID).empty().append('<option value="">' + defaultText + '</option>');
	
	for(let i = 0; i < listOfValues.length; i++) {
		$(selectID).append('<option value="' + listOfValues[i].id + '">'
				+ listOfValues[i].value + '</option>');
		
	}
	
}

/** Inserts the room data in the table with tableID 
 * @param room - room to be inserted
 * @param tableID - '#' + id of the table body (<tbody>) that will contain the room
 * @return - row in table containing the room data + index of the next column to be inserted */
function insertAttrToRoomTable(room, tableID) {
	
	var tableRef = $(tableID)[0];
	
	// Insert a row in the table at the last row
	var newRow = tableRef.insertRow(tableRef.rows.length);
	
	let index = 0; //table column index
	
	//iterate through room properties
	for (var property in room) {
	    if (room.hasOwnProperty(property)) {
	    	
	    	//id won't be displayed, reservation details also won't be displayed
	    	if(property === 'id' || property === 'reservation') {
	    		continue;
	    	}
	    	
	    	// Insert a cell in the row at index
	    	var newCell  = newRow.insertCell(index);
	    	var newText;
	    	
	    	//room price is an object containing the price and currency
	    	if(property === 'roomPrice') {
	    		newText = document.createTextNode(room[property] + '  \u20AC'); //append currency to price
	    	} else {
		    	newText  = document.createTextNode(room[property]);
	    	}
	    	
	    	newCell.appendChild(newText);
	        	
	    	index = index + 1;
	    }
	}
	
	
	return { "index" : index, "newRow": newRow };
	
	
}


function insertServiceToTable(serviceObject, tbodyID) {
	
	let tableRef = $(tbodyID)[0];
	
	// Insert a row in the table at the last row
	let newRow = tableRef.insertRow(tableRef.rows.length);
	
	//add a cell for service type
	let newCell  = newRow.insertCell(0);
	let newText  = document.createTextNode(serviceObject.service);
	newCell.appendChild(newText);
	
	//add a cell for service price
	newCell = newRow.insertCell(1);
	newText = document.createTextNode(serviceObject.servicePrice + ' \u20AC');
	newCell.appendChild(newText);
	
	
	return { "index": 2, "newRow": newRow };
	
	
}

function validateEmail(elementValue){      
	   var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	   return emailPattern.test(elementValue); 
} 