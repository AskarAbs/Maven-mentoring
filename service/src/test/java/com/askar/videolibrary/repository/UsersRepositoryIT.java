package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Users;
import com.askar.videolibrary.entity.enums.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsersRepositoryIT extends IntegrationTestBase {

    private static final Long USER_ID = 1L;
    private static UsersRepository userRepository;

    @BeforeAll
    static void initActor() {
        userRepository = context.getBean("usersRepository", UsersRepository.class);
    }

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
        var user = userRepository.findById(USER_ID);
        assertThat(user).isPresent();
        user.ifPresent(users -> users.setUsername("Monkey"));

        user.ifPresent(users -> userRepository.update(users));
        entityManager.flush();
        var actualUser = userRepository.findById(user.get().getId());

        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getUsername()).isEqualTo("Monkey");
        assertThat(actualUser.get().getId()).isEqualTo(USER_ID);
    }

    @Test
    void delete() {
        var user = userRepository.findById(USER_ID);

        user.ifPresent(value -> userRepository.delete(value));

        assertThat(userRepository.findById(USER_ID)).isEmpty();
    }

    @Test
    void findById() {
        var user = userRepository.findById(USER_ID);

        assertThat(user).isPresent();
        assertThat(user.get().getId()).isEqualTo(USER_ID);
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