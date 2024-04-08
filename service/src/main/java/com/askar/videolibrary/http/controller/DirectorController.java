package com.askar.videolibrary.http.controller;

import com.askar.videolibrary.dto.DirectorCreateEditDto;
import com.askar.videolibrary.services.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("directors", directorService.findAll());
        return "director/directors";
    }

    @GetMapping("/createDirector")
    public String createDirector() {
        return "director/createDirector";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return directorService.findById(id)
                .map(director -> {
                    model.addAttribute("director", director);
                    return "director/director";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping
    public String create(DirectorCreateEditDto director) {
        return "redirect:/directors/" + directorService.create(director).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, DirectorCreateEditDto director) {
        return directorService.update(id, director)
                .map(it -> "redirect:/directors/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!directorService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/directors";
    }


}
