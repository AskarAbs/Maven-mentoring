package com.askar.videolibrary.http.rest;

import com.askar.videolibrary.dto.film.FilmCreateEditDto;
import com.askar.videolibrary.dto.film.FilmFilter;
import com.askar.videolibrary.dto.film.FilmReadDto;
import com.askar.videolibrary.dto.PageResponse;
import com.askar.videolibrary.services.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
public class FilmsControllerRest {

    private final FilmService filmService;

    @GetMapping
    public PageResponse<FilmReadDto> findAll(FilmFilter filter, Pageable pageable) {
        var page = filmService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping(value = "/{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] findAvatar(@PathVariable("id") Long id) {
        return filmService.findAvatar(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public FilmReadDto findById(@PathVariable("id") Long id) {
        return filmService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public FilmReadDto create(@RequestBody FilmCreateEditDto film) {
        return filmService.create(film);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public FilmReadDto update(@PathVariable("id") Long id, FilmCreateEditDto film) {
        return filmService.update(id, film)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public void delete(@PathVariable("id") Long id) {
        if (!filmService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }
}
