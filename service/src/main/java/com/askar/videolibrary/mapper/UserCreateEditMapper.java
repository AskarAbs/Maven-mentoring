package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.UserCreateEditDto;
import com.askar.videolibrary.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, Users> {

    @Override
    public Users map(UserCreateEditDto object) {
        var user = new Users();
        copy(object, user);
        return user;
    }

    @Override
    public Users map(UserCreateEditDto fromObject, Users toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    public void copy(UserCreateEditDto object, Users user) {
        user.setUsername(object.getUsername());
        user.setPassword(object.getPassword());
        user.setEmail(object.getEmail());
        user.setRole(object.getRole());
    }
}
