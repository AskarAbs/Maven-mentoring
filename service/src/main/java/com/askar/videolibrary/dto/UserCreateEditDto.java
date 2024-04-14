package com.askar.videolibrary.dto;

import com.askar.videolibrary.entity.enums.Role;
import com.askar.videolibrary.validation.group.CreateAction;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    String username;

    @NotBlank(groups = CreateAction.class)
    String rawPassword;

    @Email
    String email;
    Role role;
}
