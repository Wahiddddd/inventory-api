package com.example.inventory.inventory_api.repository;

import com.example.inventory.inventory_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}