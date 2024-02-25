package services;

import com.askar.videolibrary.services.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    @Test
    void checkTestApp() {
        UserService userService = new UserService();
        var testString = userService.testApp();
        assertEquals("Ok!", testString);
    }


}
