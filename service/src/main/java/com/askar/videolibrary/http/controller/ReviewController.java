package com.askar.videolibrary.http.controller;

import com.askar.videolibrary.dto.review.ReviewCreateEditDto;
import com.askar.videolibrary.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public String create(ReviewCreateEditDto review) {
        var reviewReadDto = reviewService.create(review);
        return "redirect:/films/" + reviewReadDto.getFilm().getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, ReviewCreateEditDto review) {
        return reviewService.update(id, review)
                .map(it -> "redirect:/films/" + review.getFilmId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
