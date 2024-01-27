package MentoringTest.JunitTest;

import org.junit.jupiter.api.Test;
import services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class firstTest {

    @Test
    public void checkTestApp(){


        UserService service = new UserService();
        var testString = service.testApp();
        assertEquals("Ok!", testString);
    }
}
