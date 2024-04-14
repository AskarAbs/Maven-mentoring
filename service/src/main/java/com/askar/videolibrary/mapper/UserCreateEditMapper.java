package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.UserCreateEditDto;
import com.askar.videolibrary.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, Users> {

    private final PasswordEncoder passwordEncoder;

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
        user.setEmail(object.getEmail());
        user.setRole(object.getRole());

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
    }
}
