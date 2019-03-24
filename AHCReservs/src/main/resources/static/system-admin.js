var admins = null;

window.onload = function() {
	
	//get all available admins and add them to the select tag
	axios.get('/getAvaiableAdmins')
		.then(response => {
			let admins = response.data;
			
			for(let i = 0; i < admins.length; i++) {
				
				var select = document.getElementById("company-admin");
				var option = document.createElement("option");
				option.text = admins[i].username + " " + admins[i].firstName + " " + admins[i].lastName;
				select.add(option);
			}
			
		});
	
}

function registerCompany() {
	
	//validate form
	if(validateInputFields("#reg-company-table") === false) {
		alert("Empty field detected");
		return;
	}
	
	
	axios.post("/registerCompany", getCompanyJson())
		.then(response => alert(response.data));
}


function getCompanyJson() {
	
	var tokens = $("#company-admin :selected").text().split(" ");
	
	return {
		"name": $("#company-name").val(),
		"type":  $("#company-type :selected").val(),
		"adminUsername": tokens[0]
	}; 
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