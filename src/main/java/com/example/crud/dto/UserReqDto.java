package com.example.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserReqDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String password;
}
