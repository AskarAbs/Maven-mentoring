package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.director.DirectorCreateEditDto;
import com.askar.videolibrary.entity.Director;
import org.springframework.stereotype.Component;

@Component
public class DirectorCreateEditMapper implements Mapper<DirectorCreateEditDto, Director> {
    @Override
    public Director map(DirectorCreateEditDto object) {
        var director = new Director();
        copy(object, director);
        return director;
    }

    @Override
    public Director map(DirectorCreateEditDto fromObject, Director toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    public void copy(DirectorCreateEditDto object, Director director) {
        director.setBirthday(object.getBirthday());
        director.setFullName(object.getFullName());
    }
}
