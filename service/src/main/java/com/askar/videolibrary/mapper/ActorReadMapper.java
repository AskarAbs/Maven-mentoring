package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.actor.ActorReadDto;
import com.askar.videolibrary.entity.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorReadMapper implements Mapper<Actor, ActorReadDto> {
    @Override
    public ActorReadDto map(Actor object) {
        return new ActorReadDto(
                object.getId(),
                object.getFullName(),
                object.getBirthday()
        );
    }
}
