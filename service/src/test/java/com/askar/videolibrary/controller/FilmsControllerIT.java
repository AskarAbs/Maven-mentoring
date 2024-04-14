package com.askar.videolibrary.controller;

import com.askar.videolibrary.entity.enums.Genre;
import com.askar.videolibrary.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.askar.videolibrary.dto.FilmCreateEditDto.Fields.country;
import static com.askar.videolibrary.dto.FilmCreateEditDto.Fields.description;
import static com.askar.videolibrary.dto.FilmCreateEditDto.Fields.directorId;
import static com.askar.videolibrary.dto.FilmCreateEditDto.Fields.genre;
import static com.askar.videolibrary.dto.FilmCreateEditDto.Fields.image;
import static com.askar.videolibrary.dto.FilmCreateEditDto.Fields.name;
import static com.askar.videolibrary.dto.FilmCreateEditDto.Fields.releaseDate;
import static com.askar.videolibrary.dto.FilmCreateEditDto.Fields.trailer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class FilmsControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/films"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("film/films"))
                .andExpect(model().attributeExists("films"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/films")
                        .param(name, "cuvak-pauk")
                        .param(releaseDate, "2020-12-12")
                        .param(country, "Russia")
                        .param(genre, Genre.ADVENTURES.name())
                        .param(trailer, "test")
                        .param(description, "test")
                        .param(directorId, "1")
                        .param(image, "test"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/films/{\\d+}")
                );
    }
}
