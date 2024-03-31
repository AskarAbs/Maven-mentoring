package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.UserReadDto;
import com.askar.videolibrary.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<Users, UserReadDto> {

    @Override
    public UserReadDto map(Users object) {
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getPassword(),
                object.getEmail(),
                object.getRole()
        );
    }
}
