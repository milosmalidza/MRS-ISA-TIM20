package com.webapplication.Service;

import static org.junit.Assert.*;

import com.webapplication.Model.Hotel;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.webapplication.Model.HotelAdmin;
import com.webapplication.Repository.HotelAdminRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class HotelAdminServiceIntegrationTests {
	
	@Autowired
	private HotelAdminService hotelAdminSvc;
	
	@Test
	public void whenUsernameValid_thenFindAdmin() {
		
		String username = "ha1un";
		HotelAdmin admin = hotelAdminSvc.findByUsername(username);
		
		assertNotNull(admin);
		assertEquals(username, admin.getUsername());
		
	}

	@Test
	public void whenAdminHasHotel_thenFindHotel() {

		String username = "ha1un";
		Hotel hotel = hotelAdminSvc.getHotelForAdmin(username);

		assertNotNull(hotel);

	}



	

}
