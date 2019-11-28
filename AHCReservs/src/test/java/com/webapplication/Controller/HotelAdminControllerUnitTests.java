package com.webapplication.Controller;

import com.webapplication.JSONBeans.Username;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Service.HotelAdminService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HotelAdminControllerUnitTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    HotelAdminService hotelAdminSvcMocked;

    @Before
    public void prepData() {
        Mockito.when(hotelAdminSvcMocked.getHotelForAdmin("testAdmin")).thenReturn(new Hotel());
    }

    @Test
    public void testGetHotelForAdmin() {

        Username username = new Username();
        username.setUsername("testAdmin");

        ResponseEntity<Hotel> response =
                testRestTemplate.postForEntity("/hotelAdmin/getHotel", username, Hotel.class);

        Hotel hotel = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(hotel);

    }

}
