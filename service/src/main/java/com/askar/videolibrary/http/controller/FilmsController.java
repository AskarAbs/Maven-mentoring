package com.askar.videolibrary.http.controller;

import com.askar.videolibrary.config.AuditConfiguration;
import com.askar.videolibrary.dto.PageResponse;
import com.askar.videolibrary.dto.film.FilmCreateEditDto;
import com.askar.videolibrary.dto.film.FilmFilter;
import com.askar.videolibrary.entity.enums.Genre;
import com.askar.videolibrary.services.DirectorService;
import com.askar.videolibrary.services.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmsController {

    private final FilmService filmService;
    private final DirectorService directorService;
    private final AuditConfiguration auditConfiguration;

    @PostMapping("/create-review/{id}")
    public String createReview(@PathVariable("id") Long id, Model model) {
        return filmService.findById(id)
                .map(film -> {
                    model.addAttribute("film", film);
                    model.addAttribute("email", auditConfiguration.auditorAware().getCurrentAuditor().orElseThrow());
                    return "review/createReview";
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping
    public String findAll(Model model, FilmFilter filter, Pageable pageable) {
        var page = filmService.findAll(filter, pageable);
        model.addAttribute("films", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("null", null);
        model.addAttribute("genres", Genre.values());
        return "film/films";
    }

    @GetMapping("/create-film")
    public String createFilm(Model model) {
        model.addAttribute("genres", Genre.values());
        model.addAttribute("directors", directorService.findAll());
        return "film/createFilm";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return filmService.findById(id)
                .map(film -> {
                    model.addAttribute("film", film);
                    model.addAttribute("genres", Genre.values());
                    model.addAttribute("directors", directorService.findAll());
                    return "film/film";
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping
    public String create(@Validated FilmCreateEditDto film, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("film", film);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/films/createFilm";
        }
        return "redirect:/films/" + filmService.create(film).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, FilmCreateEditDto film) {
        return filmService.update(id, film)
                .map(it -> "redirect:/films/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!filmService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/films";
    }
}
