package services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceIT {

    @Test
    public void checkSetName(){
        UserService userService = new UserService();
        var user = userService.setName();
        assertEquals("Ivan",user.getName());
    }

    @Test
    public void checkTestApp(){

        UserService service = new UserService();
        var testString = service.testApp();
        assertEquals("Ok!", testString);
    }
}