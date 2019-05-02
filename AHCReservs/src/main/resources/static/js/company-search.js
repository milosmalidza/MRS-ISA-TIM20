

window.addEventListener("wheel", switchService, false);

var serviceIndex = 0;
var switching = false;

function switchService(e) {
	
	var filename= location.pathname.split('/').pop(); //get the html file name which called the function
	var param = ""; // the parameter that indicates a hotel or rent a car service
	
	if(filename.includes("car")) {
		param = "car";
	} else {
		param = "hotel";
	}
	
	console.log(e.deltaY);
	if (e.deltaY > 0) {
		scrollDownDetected(param);
	}
	else {
		scrollUpDetected(param);
	}
	
}

function scrollDownDetected(companyTypePrefix) {
	if (serviceIndex < services.length - 1 && !switching) {
		switching = true;
		serviceIndex++;
		switchServiceInfo(companyTypePrefix);
		
	}
}

function switchServiceInfo(companyTypePrefix) {
	$(".company-number-holder").animate({top : (- serviceIndex * 100) + "%"}, 300);
		$("." + companyTypePrefix + "-services-background-holder").fadeOut(300);
		$(".main-company-name").animate({opacity : 0}, 300);
		setTimeout(function() {
			
			if (services[serviceIndex].name.length > 20) $(".main-company-name").css("font-size", "3vw");
			else if (services[serviceIndex].name.length > 15) $(".main-company-name").css("font-size", "4vw");
			else if (services[serviceIndex].name.length > 10) $(".main-company-name").css("font-size", "5vw");
			
			$(".main-company-name").html(services[serviceIndex].name);
			$(".main-company-name").animate({opacity : 1}, 300);
			$("." + companyTypePrefix + "-services-background-holder").fadeIn(300);
		}, 300);
		
		$(".main-company-rating").animate({opacity : 0}, 300);
		setTimeout(function() {
			$(".main-company-rating").html(services[serviceIndex].rating.toFixed(1) + " / 5.0");
			$(".main-company-rating").animate({opacity : 1}, 300);
		}, 300);
		
		setTimeout(function(){
			$(".main-company-description").animate({opacity : 0}, 300);
			setTimeout(function() {
				$(".main-company-description").html(services[serviceIndex].description);
				$(".main-company-description").animate({opacity : 1}, 300);
			}, 300)
		},150);
		
		setTimeout(function(){
			$(".main-company-address").animate({opacity : 0}, 300);
			setTimeout(function() {
				$(".main-company-address").html(services[serviceIndex].address);
				$(".main-company-address").animate({opacity : 1}, 300);
				switching = false;
			}, 300);
		},300);	
}


function scrollUpDetected(companyTypePrefix) {
	if (serviceIndex > 0 && !switching) {
		switching = true;
		serviceIndex--;
		switchServiceInfo(companyTypePrefix);
		
	}
}



function exploreEnter() {
	var el = document.getElementById("company-explore-mouse-enter");
	el.style.webkitTransform = "translate(-50%, -50%) scale(1.00)";
	el.style.transform = "translate(-50%, -50%) scale(1.00)";
}

function exploreLeave() {
	var el = document.getElementById("company-explore-mouse-enter");
	el.style.webkitTransform = "translate(-50%, -50%) scale(0.00)";
	el.style.transform = "translate(-50%, -50%) scale(0.00)";
}




function browseCompaniesLoaded() {
	
	if (services.length == 0) return;
	
	var companyNumber = document.getElementsByClassName("company-number-holder")[0];
	var companyName = document.getElementsByClassName("main-company-name")[0];
	var companyDescription = document.getElementsByClassName("main-company-description")[0];
	var companyAddress = document.getElementsByClassName("main-company-address")[0];
	var companyRating = document.getElementsByClassName("main-company-rating")[0];
	
	companyNumber.innerHTML = "";
	
	for (var i = 0; i < services.length; i++) {
		var element = document.createElement("div");
		element.innerHTML = (i + 1) + "#";
		companyNumber.appendChild(element)
	}
	
	companyName.innerHTML = services[0].name;
	companyDescription.innerHTML = services[0].description;
	companyAddress.innerHTML = services[0].address;
	companyRating.innerHTML = services[0].rating.toFixed(1) + " / 5.0";
	
	
	
	if (services[0].name.length > 20) {
		companyName.style.fontSize = "3vw";
	}
	
	else if (services[0].name.length > 15) {
		companyName.style.fontSize = "4vw";
	}
	
	
	console.log(services.length);
}


function companyClicked() {
	loadPage("vehicle-search.html?id=" + services[serviceIndex].id);
}






































