

function initHomePage() {
	var rotatedEl = document.getElementById("absolute-div-1");
	
	setTimeout(function() {
		rotatedEl.style.width = "45%";
		
		window.addEventListener("wheel", MouseWheelHandler, false);
		
	}, 2000);
}

var wheelTriggered = false;

var is_second_div_up = false;

function MouseWheelHandler(e) {
	
	if (wheelTriggered) return;
	
	
	var wheelDirection = e.wheelDelta;
	
	if (wheelDirection < 0) {
		scrolledDown();
	}
	else {
		scrolledUp();
	}
}


function scrolledDown() {
	if (is_second_div_up) return;
	is_second_div_up = true;
	wheelTriggered = true;
	var secondDiv = document.getElementById("secondary-div");
	secondDiv.style.top = "0%";
	
	setTimeout(function() {
		wheelTriggered = false;
		document.getElementById("third-div").style.display = "none";
		var teaser = document.getElementById("white-teaser");
		teaser.style.width = "0%";
		teaser.style.height = "0%";
		
		
	}, 1200);
	
	
}

function scrolledUp() {
	
	if (!is_second_div_up) return;
	is_second_div_up = false;
	wheelTriggered = true;
	
	var teaser = document.getElementById("white-teaser");
	teaser.style.width = "100%";
	teaser.style.height = "100%";
	
	
	setTimeout(function() {
		wheelTriggered = false;
		document.getElementById("third-div").style.display = "block";
		var secondDiv = document.getElementById("secondary-div");
		secondDiv.style.top = "100%";
		
	}, 1000);
	
}



























