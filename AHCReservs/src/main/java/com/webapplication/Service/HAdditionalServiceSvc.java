package com.webapplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.HAdditionalService;
import com.webapplication.Model.HotelServiceType;
import com.webapplication.Repository.HAdditionalServiceRepository;

@Service("HAdditionalServiceSvc")
public class HAdditionalServiceSvc {

	@Autowired
	HAdditionalServiceRepository hServiceRep;
	
	
	public boolean serviceExistsInHotel(HotelServiceType serviceType, Long hotelID) {
		
		
		for(HAdditionalService additionalSvc: findAllForHotel(hotelID)) {
			
			if(additionalSvc.getService() == serviceType) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
	
	public HotelServiceType convertStringToService(String service) {
		
		try {
			
			return HotelServiceType.valueOf(service.toUpperCase());
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
		 
		
	}
	
	
	/***** Database repository methods ******/
	
	public Optional<HAdditionalService> findOne(Long id) {
		
		return hServiceRep.findById(id);
		
	}
	
	public HAdditionalService save(HAdditionalService service) {
		
		return hServiceRep.save(service);
	}
	
	public List<HAdditionalService> findAll() {
		return hServiceRep.findAll();
	}
	
	
	public List<HAdditionalService> findAllForHotel(Long hotelID) {
		
		return hServiceRep.findAllForHotel(hotelID);
		
	}
	
	
	public HAdditionalService findOneForHotel(Long hotelID, HotelServiceType serviceType) {
		
		return hServiceRep.findOneForHotel(hotelID, serviceType);
		
	}
	
	
}
