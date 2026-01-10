package com.example.inventory.inventory_api.controller;

import com.example.inventory.inventory_api.DTO.request.ProductRequest;
import com.example.inventory.inventory_api.DTO.response.ProductResponse;
import com.example.inventory.inventory_api.service.ProductService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}/sell")
    public ResponseEntity<?> sell(@PathVariable Long id, @RequestParam int qty) {
        service.sell(id, qty);
        return ResponseEntity.ok(Map.of("message", "Stock updated"));
    }
}
