package com.askar.videolibrary.service;

import com.askar.videolibrary.dto.user.UserCreateEditDto;
import com.askar.videolibrary.entity.Users;
import com.askar.videolibrary.entity.enums.Role;
import com.askar.videolibrary.repository.IntegrationTestBase;
import com.askar.videolibrary.repository.UsersRepository;
import com.askar.videolibrary.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private final UserService userService;
    private final UsersRepository usersRepository;

    @Test
    void findAll() {
        var result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    void findById() {
        var user = getUser();
        var savedUser = usersRepository.save(user);
        var actualUser = userService.findById(savedUser.getId());
        assertTrue(actualUser.isPresent());
        actualUser.ifPresent(mbUser -> assertEquals("Askar", mbUser.getUsername()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "Askar",
                "1210!",
                "askar123@gmail.com",
                Role.USER
        );
        var actualUserReadDto = userService.create(userDto);
        assertThat(actualUserReadDto.getId()).isNotNull();
        assertThat(userDto.getEmail()).isEqualTo(actualUserReadDto.getEmail());
        assertThat(userDto.getRawPassword()).isEqualTo(actualUserReadDto.getPassword());
        assertThat(userDto.getUsername()).isEqualTo(actualUserReadDto.getUsername());
        assertThat(userDto.getRole()).isSameAs(actualUserReadDto.getRole());
    }

    @Test
    void update() {
        var user = getUser();
        var savedUser = usersRepository.save(user);
        UserCreateEditDto userDto = new UserCreateEditDto(
                "Dima",
                "1210!",
                "askar123@gmail.com",
                Role.USER
        );
        var actualResult = userService.update(savedUser.getId(), userDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(mbUser ->
        {
            assertThat(userDto.getEmail()).isEqualTo(mbUser.getEmail());
            assertThat(userDto.getRawPassword()).isEqualTo(mbUser.getPassword());
            assertThat(userDto.getUsername()).isEqualTo(mbUser.getUsername());
            assertThat(userDto.getRole()).isSameAs(mbUser.getRole());
        });
    }

    @Test
    void delete() {
        var user = getUser();
        var savedUser = usersRepository.save(user);
        var result = userService.delete(savedUser.getId());
        assertTrue(result);
        assertThat(usersRepository.findById(savedUser.getId())).isEmpty();
        assertFalse(userService.delete(-12142L));
    }

    public Users getUser() {
        return Users.builder()
                .username("Askar")
                .password("1210!")
                .email("askar123@gmail.com")
                .role(Role.USER)
                .build();
    }
}
