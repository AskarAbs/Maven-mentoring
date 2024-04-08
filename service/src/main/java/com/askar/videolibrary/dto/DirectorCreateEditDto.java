package com.askar.videolibrary.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class DirectorCreateEditDto {

    String fullName;

    LocalDate birthday;
}
