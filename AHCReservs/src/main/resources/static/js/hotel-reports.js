var reportControllerPath = "/hotelReports";

var startDate = null;
var endDate = null;

var myChart;


function initDatePicker() {
	
	$(function() {
		$('input[name="daterange"]').daterangepicker({
			timePicker: false,
	    	opens: 'left',
	    	minDate: 0
	  	}, function(start, end, label) {
	           
	  		 startDate = start.format('DD.MM.YYYY.');
	  		 endDate = end.format('DD.MM.YYYY.');
		  
	    });
	});
	
}

function getReport() {
	
	let reportTypeSelected = $("#report-type").val();
	
	if(reportTypeSelected == null) {
		toast("Please select report type");
		return;
	}
	
	if(reportTypeSelected != "room-ratings") {
		if(startDate == null || endDate == null) {
			toast("Please select a start and end date");
			return;
		}
	}
	

	switch(reportTypeSelected) {
	
	case "incomes":
		getGraphData("/getIncomeGraph", "Incomes for selected period", getGraphDataJson());
		break;
		
	case "visits":
		getGraphData("/getVisitsGraph", "Visits for selected period", getGraphDataJson());
		break;
		
	case "room-ratings":
		getGraphData("/getRoomRatingsGraph", "Room ratings", getHotelIdJson());
		break;
	
	}

	
}


function getGraphData(methodPath, chartTitle, json) {
	
	axios.post(reportControllerPath + methodPath, json)
		.then(response => {
			
			if(response.data != "" && response.data != null) {
				
				if(chartTitle.toLowerCase() == "room ratings") {
					drawGraph('bar', chartTitle, response.data.labels, response.data.dataframes, 'rgba(30, 165, 9, 1)');
				} else {
					drawGraph('line', chartTitle, response.data.labels, response.data.dataframes, 'rgba(255, 255, 255, 0)');
				}
				
			}
			
		});
	
}

function getGraphDataJson() {
	
	return {
		"companyID": adminHotel.id,
		"strStartDate": startDate,
		"strEndDate": endDate
	}
	
}

function getHotelIdJson() {
	return {"key": adminHotel.id}
}
	

function drawGraph(graphType, chartTitle, graphLabels, graphData, backgroundColor) {
	
	
	if(myChart != undefined) {
		myChart.destroy();
	}
	
	
	let ctx = document.getElementById('myChart').getContext('2d');
	myChart = new Chart(ctx, {
	    type: graphType,
	    data: {
	        labels: graphLabels,
	        datasets: [{
	            label: chartTitle,
	            data: graphData,
	            backgroundColor: backgroundColor,
	            borderColor: 'rgba(2, 91, 10, 1)',
	            borderWidth: 3
	        }]
	    },
	    options: {
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                }
	            }]
	        }
	    }
	});
	
}
	