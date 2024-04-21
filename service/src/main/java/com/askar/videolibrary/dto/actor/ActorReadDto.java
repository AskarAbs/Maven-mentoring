package com.askar.videolibrary.dto.actor;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ActorReadDto {

    Long id;
    String fullName;
    LocalDate birthday;

}
