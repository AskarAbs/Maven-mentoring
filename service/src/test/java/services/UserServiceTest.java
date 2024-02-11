package services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    @Test
    public void checkTestApp() {

        UserService userService = new UserService();
        var testString = userService.testApp();
        assertEquals("Ok!", testString);
    }

}
