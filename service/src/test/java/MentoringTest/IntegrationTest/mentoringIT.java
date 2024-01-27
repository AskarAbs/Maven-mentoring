package MentoringTest.IntegrationTest;

import org.junit.jupiter.api.Test;
import services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class mentoringIT {

    @Test
    public void checkService(){
        UserService user1 = new UserService();
        var user = user1.setName();
        assertEquals("Ivan",user.getName());
    }
}
