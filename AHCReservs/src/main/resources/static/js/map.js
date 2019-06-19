var mapControllerPath = "/map"; 
var map;

var company = null;
var companyType = "";

var editingMap = false;

/** Funkcija za instanciranje mape i sacuvanog pina u sklopu mape
 * @param: cmpny - instanca kompanije(hotel, rent a car)
 * @param: type - string vrednost koja predstavlja tip kompanije ('hotel', 'rent a car')
 * @param: editing - bool vrednost koja diktira da li ce se na dvoklik dodavati pinovi (za admine true, u suprotnom false)  */
function loadMap(cmpny, type, editing) {
	
	company = cmpny;
	companyType = type;
	editingMap = editing;
	
	/*
	let online = navigator.onLine;
	
	//if there is no internet connection the map won't load
	if(online === false) {
		NotifyUser("Map loading", "No internet connection");
		return;
	}*/
	
	let loadedPin = initMap(); //map initialization
	
	if(editingMap === false) {
		return loadedPin;
	}
	
	//adding event listeners to map
	Microsoft.Maps.Events.addHandler(map, 'dblclick', function(e) {
		
		addPushPin(e.location);
		
	});
}


function initMap() {
	
	//loading pushpin if it exists
	loadedPin = loadPin();
	
	if(loadedPin == null) {
		//centering the map by default
		map = new Microsoft.Maps.Map(document.getElementById('companyMap'), {});
	} else {
		//centering map around the pin
		map = new Microsoft.Maps.Map(document.getElementById('companyMap'), {
			center: loadedPin.getLocation()
		});
		map.entities.push(loadedPin);
	}	
	
	return loadedPin;
	
}


function addPushPin(mouseLocation) {
	
	if(company == null) {
		return;
	}
	
	//remove pushpin in case it is already on the map
	removePin();
	
	//creating pushpin
	let pin = new Microsoft.Maps.Pushpin(mouseLocation, {
        title: company.name,
        subTitle: company.address,
        color: 'red'
    });
	
	//adding pin to map
	map.entities.push(pin); 
	
	//saving pin for the right company
	switch(companyType) {
	
	case "hotel":
		saveHotelPin(mouseLocation);
		return;
		
	case "rent a car":
		saveRentACarPin(mouseLocation);
		return;
	
	}
	
}


function saveHotelPin(mouseLocation) {
	
	axios.post(mapControllerPath + "/saveHotelPin", getPinJson(mouseLocation))
		.then(response => {
			
			if(response.data != "" && response.data != null) {
				company = response.data;
			}
			
		});
	
}


function saveRentACarPin(mouseLocation) {
	
	axios.post(mapControllerPath + "/saveRentACarPin", getPinJson(mouseLocation));
}



function getPinJson(location) {
	
	return {
		"companyID": company.id,
		"latitude": location.latitude,
		"longitude": location.longitude
	}
	
}


function loadPin() {
	
	if(company == null) {
		return null;
	}
	
	if(company.latitude == null || company.longitude == null) {
		return null;
	}
	
	pinLocation = new Microsoft.Maps.Location(company.latitude, company.longitude);
	
	//creating pushpin
	let pin = new Microsoft.Maps.Pushpin(pinLocation, {
        title: company.name,
        subTitle: company.address,
        color: 'red'
    });
	
	
	return pin;
	
	
}


function removePin() {
	
	if(map.entities.getLength() > 0) {
		map.entities.clear();
	}
	
}
