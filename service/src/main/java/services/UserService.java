package services;



import entity.User;

public class UserService {

    private final User user = new User();


    public User setName(){
        user.setName("Ivan");
        return user;
    }

    public String testApp(){
        return "Ok!";
    }
}
