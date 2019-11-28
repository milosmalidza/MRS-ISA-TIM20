package com.webapplication.Repository;

import com.webapplication.Model.HotelAdmin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-repo.properties") // -> to switch .properties file
public class HotelAdminRepositoryIntegrationTests {

    @Autowired
    HotelAdminRepository hotelAdminRepo;

    @Test
    public void whenInvalidUsername_returnNull() {
        String username = "skibitupapa";
        HotelAdmin admin = hotelAdminRepo.findByUsername(username);

        assertNull(admin);
    }
}
