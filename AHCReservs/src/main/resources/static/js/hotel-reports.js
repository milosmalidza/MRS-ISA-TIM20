var reportControllerPath = "/hotelReports";

var startDate = null;
var endDate = null;


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
	
	if(startDate == null || endDate == null) {
		toast("Please select a start and end date");
		return;
	}
	
	
	if(reportTypeSelected == "incomes") {
		getGraphData("/getIncomeGraph", "Incomes for selected period");
	} else {
		getGraphData("/getVisitsGraph", "Visits for selected period");
	}
	
}


function getGraphData(methodPath, chartTitle) {
	
	axios.post(reportControllerPath + methodPath, getGraphDataJson())
		.then(response => {
			
			if(response.data != "" && response.data != null) {
				drawGraph(chartTitle, response.data.labels, response.data.dataframes);
			}
			
		})
	
}

function getGraphDataJson() {
	
	return {
		"companyID": adminHotel.id,
		"strStartDate": startDate,
		"strEndDate": endDate
	}
	
}
	
	
	
function drawGraph(chartTitle, graphLabels, graphData) {
	
	let ctx = document.getElementById('myChart').getContext('2d');
	
	var myChart = new Chart(ctx, {
	    type: 'line',
	    data: {
	        labels: graphLabels,
	        datasets: [{
	            label: chartTitle,
	            data: graphData,
	            backgroundColor: 'rgba(255, 255, 255, 0)',
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
	