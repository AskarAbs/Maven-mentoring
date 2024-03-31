package com.askar.videolibrary.dto;

import com.askar.videolibrary.entity.enums.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    String username;
    String password;
    String email;
    Role role;
}
