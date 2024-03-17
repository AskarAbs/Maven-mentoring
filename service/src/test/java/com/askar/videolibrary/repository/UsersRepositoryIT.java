package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Users;
import com.askar.videolibrary.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class UsersRepositoryIT extends IntegrationTestBase {

    private final UsersRepository userRepository;

    @Test
    void findAll() {
        var users = userRepository.findAll();

        assertThat(users).hasSize(2);
    }

    @Test
    void save() {
        var user = createUser();

        var actualUser = userRepository.save(user);

        assertThat(actualUser.getId()).isNotNull();
    }


    @Test
    void update() {
        var createdUser = createUser();
        var savedUser = userRepository.save(createdUser);

        var user = userRepository.findById(savedUser.getId());
        assertThat(user).isPresent();
        user.ifPresent(users -> users.setUsername("Monkey"));

        user.ifPresent(userRepository::update);
        entityManager.flush();
        var actualUser = userRepository.findById(user.get().getId());

        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getUsername()).isEqualTo("Monkey");
        assertThat(actualUser.get().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    void delete() {
        var createdUser = createUser();
        var savedUser = userRepository.save(createdUser);

        var user = userRepository.findById(savedUser.getId());

        user.ifPresent(userRepository::delete);

        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
    }

    @Test
    void findById() {
        var createdUser = createUser();
        var savedUser = userRepository.save(createdUser);

        var user = userRepository.findById(savedUser.getId());

        assertThat(user).isPresent();
        assertThat(user.get().getId()).isEqualTo(savedUser.getId());
    }

    public Users createUser() {
        return Users.builder()
                .username("123askar")
                .email("123askar@gmail.com")
                .password("1234")
                .role(Role.USER)
                .build();
    }
}