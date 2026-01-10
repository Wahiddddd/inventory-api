package com.example.inventory.inventory_api.DTO.request;

import lombok.*;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}

