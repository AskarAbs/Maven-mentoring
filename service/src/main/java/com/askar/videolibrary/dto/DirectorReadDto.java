package com.askar.videolibrary.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class DirectorReadDto {

    Long id;
    String fullName;
    LocalDate birthday;
}
