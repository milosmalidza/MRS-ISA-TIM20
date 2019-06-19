package com.webapplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.JSONBeans.DateBean;
import com.webapplication.JSONBeans.GraphData;
import com.webapplication.Service.RoomReservationService;

@RestController
@RequestMapping("/hotelReports")
public class HotelReportsController {
	
	
	@Autowired
	RoomReservationService roomReservSvc;
	
	@RequestMapping(
			value="/getIncomeGraph",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GraphData> getIncomeGraph(@RequestBody DateBean dateBean) {
		
		return new ResponseEntity<>(roomReservSvc.getIncomeGraphData(dateBean), HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value="/getVisitsGraph",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GraphData> getVisitsGraph(@RequestBody DateBean dateBean) {
		
		return new ResponseEntity<>(roomReservSvc.getVisitsGraphData(dateBean), HttpStatus.OK);
		
	}

}
