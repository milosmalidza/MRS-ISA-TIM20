package com.webapplication.Repository;

import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelAdmin;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-repo.properties")
public class HotelAdminRepositoryUnitTests {

    @Autowired
    private HotelAdminRepository hotelAdminRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Before
    public void prepData() {

        Hotel testHotel = new Hotel();
        testHotel.setName("testHotel");

        HotelAdmin admin1 = new HotelAdmin("admin1", "pw", "", "", "", null);
        admin1.setEnabled(true);

        HotelAdmin admin2 = new HotelAdmin("admin2", "pw", "", "", "", null);
        admin2.setEnabled(true);

        HotelAdmin admin3 = new HotelAdmin("admin3", "pw", "", "", "", testHotel);
        admin3.setEnabled(true);

        HotelAdmin admin4 =new HotelAdmin("admin4", "pw", "", "", "", null);
        admin4.setEnabled(false);

        testEntityManager.persist(admin1);
        testEntityManager.persist(admin2);
        testEntityManager.persist(admin3);
        testEntityManager.persist(admin4);

        testEntityManager.flush();


    }

    @Test
    public void whenAdminsWithoutHotel_returnEnabledAdmins() {

        List<HotelAdmin> availableAdmins = hotelAdminRepo.getAvailableAdmins();

        assertEquals(2, availableAdmins.size());
        assertNull(availableAdmins.get(0).getHotel());
        assertTrue(availableAdmins.get(0).isEnabled());

    }
}
