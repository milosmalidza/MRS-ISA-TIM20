package com.webapplication.Service;

import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Repository.HotelAdminRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class HotelAdminServiceUnitTests {

    @Autowired
    HotelAdminService hotelAdminSvc;

    @MockBean
    private HotelAdminRepository hotelAdminRepoMocked;

    @Before
    public void setUpRepo() {
        String username = "someAdmin";
        HotelAdmin admin = new HotelAdmin();

        admin.setHotel(new Hotel());
        admin.setUsername(username);

        Mockito.when(hotelAdminRepoMocked.findByUsername(username)).thenReturn(admin);
    }

    @Test
    public void whenUsernameValid_thenReturnAdmin() {

        String username = "someAdmin";

        HotelAdmin admin = hotelAdminSvc.findByUsername(username);

        assertNotNull(admin);
        assertEquals(username, admin.getUsername());


    }

    @Test
    public void whenAdminHasHotel_thenReturnHotel() {

        String username = "someAdmin";
        Hotel hotel = hotelAdminSvc.getHotelForAdmin(username);

        assertNotNull(hotel);

    }

}
