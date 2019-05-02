

function initHomePage() {
	var rotatedEl = document.getElementById("absolute-div-1");
	
	var leaf = document.getElementById("leaf-animation");
	
	$("body").removeClass("preload");
	
	leaf.style.animationName = "leafStrokeFinish";
	leaf.style.animationDuration = "2s";
	leaf.style.animationIterationCount = "1";
	setTimeout(function() {
		leaf.style.transition = "fill 1s";
		leaf.style.webkitTransition = "fill .5s";
		leaf.style.fill = "rgba(118,200,94,1.00)";
	}, 1900);
	
	setTimeout(function() {
		rotatedEl.style.width = "45%";
		
		window.addEventListener("wheel", MouseWheelHandler, false);
		
	}, 100);
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
	
	var teaser = document.getElementById("white-teaser");
	teaser.style.transition = "ease-in .6s";
	teaser.style.webkitTransition = "ease-in .6s";
	teaser.style.top = "0%";
	
	setTimeout(function() {
		wheelTriggered = false;
		document.getElementById("third-div").style.display = "none";
		document.getElementById("secondary-div").style.display = "block";
		teaser.style.transition = "ease-out .6s";
		teaser.style.webkitTransition = "ease-out .6s";
		teaser.style.top = "-100%";
		
		
		
	}, 620);
	
	
}

function scrolledUp() {
	
	if (!is_second_div_up) return;
	is_second_div_up = false;
	wheelTriggered = true;
	
	var teaser = document.getElementById("white-teaser");
	teaser.style.transition = "ease-in .6s";
	teaser.style.webkitTransition = "ease-in .6s";
	teaser.style.top = "0%";
	
	
	setTimeout(function() {
		wheelTriggered = false;
		document.getElementById("third-div").style.display = "block";
		document.getElementById("secondary-div").style.display = "none";
		teaser.style.transition = "ease-out .6s";
		teaser.style.webkitTransition = "ease-out .6s";
		teaser.style.top = "100%";
		
		
	}, 620);
	
}


function mouseEnterItem(element) {
	var animatedDiv = element.getElementsByClassName("item-animation-div")[0];
	animatedDiv.style.height = "100%";
}



function mouseLeaveItem(element) {
	var animatedDiv = element.getElementsByClassName("item-animation-div")[0];
	animatedDiv.style.height = "0%";
}

























