package com.webapplication.Controller;

import com.webapplication.JSONBeans.Username;
import com.webapplication.Model.Hotel;
import com.webapplication.Service.HotelAdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class HotelAdminControllerIntegrationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    HotelAdminService hotelAdminSvc;

    @Test
    public void givenValidUsername_whenAdminHasHotel_returnHotel() {

        Username username = new Username();
        username.setUsername("ha1un");

        ResponseEntity<Hotel> response =
                restTemplate.postForEntity("/hotelAdmin/getHotel", username, Hotel.class);

        Hotel hotel = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(hotel);

    }

}
