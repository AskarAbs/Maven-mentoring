package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.DirectorReadDto;
import com.askar.videolibrary.entity.Director;
import org.springframework.stereotype.Component;

@Component
public class DirectorReadMapper implements Mapper<Director, DirectorReadDto> {
    @Override
    public DirectorReadDto map(Director object) {
        return new DirectorReadDto(
                object.getId(),
                object.getFullName(),
                object.getBirthday()
        );
    }
}
