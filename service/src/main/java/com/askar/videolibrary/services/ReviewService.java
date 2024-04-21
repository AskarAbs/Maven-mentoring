package com.askar.videolibrary.services;

import com.askar.videolibrary.dto.review.ReviewCreateEditDto;
import com.askar.videolibrary.dto.review.ReviewReadDto;
import com.askar.videolibrary.mapper.ReviewCreateEditMapper;
import com.askar.videolibrary.mapper.ReviewReadMapper;
import com.askar.videolibrary.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewCreateEditMapper reviewCreateEditMapper;
    private final ReviewReadMapper reviewReadMapper;

    @Transactional
    public ReviewReadDto create(ReviewCreateEditDto review) {
        return Optional.of(review)
                .map(reviewCreateEditMapper::map)
                .map(reviewRepository::save)
                .map(reviewReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ReviewReadDto> update(Long id, ReviewCreateEditDto review) {
        return reviewRepository.findById(id)
                .map(entity -> reviewCreateEditMapper.map(review, entity))
                .map(reviewRepository::saveAndFlush)
                .map(reviewReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return reviewRepository.findById(id)
                .map(entity -> {
                    reviewRepository.delete(entity);
                    reviewRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
