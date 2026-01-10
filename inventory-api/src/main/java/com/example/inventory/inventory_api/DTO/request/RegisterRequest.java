package com.example.inventory.inventory_api.DTO.request;

import com.example.inventory.inventory_api.entity.Role;
import lombok.*;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private Role role; // ADMIN / STAFF
}

