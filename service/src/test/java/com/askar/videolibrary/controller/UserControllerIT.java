package com.askar.videolibrary.controller;

import com.askar.videolibrary.entity.enums.Role;
import com.askar.videolibrary.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.askar.videolibrary.dto.UserCreateEditDto.Fields.email;
import static com.askar.videolibrary.dto.UserCreateEditDto.Fields.rawPassword;
import static com.askar.videolibrary.dto.UserCreateEditDto.Fields.role;
import static com.askar.videolibrary.dto.UserCreateEditDto.Fields.username;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(username, "test")
                        .param(rawPassword, "123")
                        .param(email, "test123@gmail.com")
                        .param(role, String.valueOf(Role.USER)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/login/**")
                );
    }
}
