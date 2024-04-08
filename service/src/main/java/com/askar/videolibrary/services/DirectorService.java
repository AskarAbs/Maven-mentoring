package com.askar.videolibrary.services;

import com.askar.videolibrary.dto.DirectorCreateEditDto;
import com.askar.videolibrary.dto.DirectorReadDto;
import com.askar.videolibrary.mapper.DirectorCreateEditMapper;
import com.askar.videolibrary.mapper.DirectorReadMapper;
import com.askar.videolibrary.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectorService {

    public final DirectorReadMapper directorReadMapper;
    public final DirectorRepository directorRepository;
    public final DirectorCreateEditMapper directorCreateEditMapper;

    public List<DirectorReadDto> findAll() {
        return directorRepository.findAll().stream()
                .map(directorReadMapper::map)
                .toList();
    }

    public Optional<DirectorReadDto> findById(Long id) {
        return directorRepository.findById(id)
                .map(directorReadMapper::map);
    }

    @Transactional
    public DirectorReadDto create(DirectorCreateEditDto director) {
        return Optional.of(director)
                .map(directorCreateEditMapper::map)
                .map(directorRepository::save)
                .map(directorReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<DirectorReadDto> update(Long id, DirectorCreateEditDto director) {
        return directorRepository.findById(id)
                .map(entity -> directorCreateEditMapper.map(director, entity))
                .map(directorRepository::saveAndFlush)
                .map(directorReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return directorRepository.findById(id)
                .map(entity -> {
                    directorRepository.delete(entity);
                    directorRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
