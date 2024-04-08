package com.askar.videolibrary.services;

import com.askar.videolibrary.dto.FilmCreateEditDto;
import com.askar.videolibrary.dto.FilmFilter;
import com.askar.videolibrary.dto.FilmReadDto;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.mapper.FilmCreateEditMapper;
import com.askar.videolibrary.mapper.FilmReadMapper;
import com.askar.videolibrary.repository.FilmRepository;
import com.askar.videolibrary.repository.filter.QPredicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.askar.videolibrary.entity.QFilm.film;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilmService {

    private final FilmRepository filmRepository;
    private final FilmReadMapper filmReadMapper;
    private final FilmCreateEditMapper filmCreateEditMapper;
    private final ImageService imageService;


    public Page<FilmReadDto> findAll(FilmFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getName(), film.name::eq)
                .add(filter.getGenre(), film.genre::eq)
                .add(filter.getCountry(), film.country::eq)
                .buildOr();

        return filmRepository.findAll(predicate, pageable)
                .map(filmReadMapper::map);
    }

    public Optional<FilmReadDto> findById(Long id) {
        return filmRepository.findById(id)
                .map(filmReadMapper::map);
    }

    @Transactional
    public FilmReadDto create(FilmCreateEditDto film) {
        return Optional.of(film)
                .map(filmCreateEditDto -> {
                    uploadImage(filmCreateEditDto.getImage());
                    return filmCreateEditMapper.map(filmCreateEditDto);
                })
                .map(filmRepository::save)
                .map(filmReadMapper::map)
                .orElseThrow();
    }
    public Optional<byte[]> findAvatar(Long id) {
        return filmRepository.findById(id)
                .map(Film::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Transactional
    public Optional<FilmReadDto> update(Long id, FilmCreateEditDto dto) {
        return filmRepository.findById(id)
                .map(film -> {
                    uploadImage(dto.getImage());
                    return filmCreateEditMapper.map(dto,film);
                })
                .map(filmRepository::saveAndFlush)
                .map(filmReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return filmRepository.findById(id)
                .map(entity -> {
                    filmRepository.delete(entity);
                    filmRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
