package com.example.crud.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResDto {
    private UUID id;
    private UUID tenantId;
    private String name;
    private String password;
    private LocalDateTime createdAt;
}
