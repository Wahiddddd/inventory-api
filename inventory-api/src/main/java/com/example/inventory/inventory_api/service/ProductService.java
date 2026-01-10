package com.example.inventory.inventory_api.service;

import com.example.inventory.inventory_api.DTO.request.ProductRequest;
import com.example.inventory.inventory_api.DTO.response.ProductResponse;
import com.example.inventory.inventory_api.entity.Product;
import com.example.inventory.inventory_api.exception.ResourceNotFoundException;
import com.example.inventory.inventory_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.*;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Cacheable("products")
    public List<ProductResponse> getAll() {
        log.info("Fetching all products");
        return repository.findAll()
                .stream()
                .map(p -> new ProductResponse(
                        p.getId(), p.getName(), p.getPrice(), p.getStock()))
                .toList();
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse create(ProductRequest request) {
        log.info("Creating product {}", request.getName());
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        repository.save(product);

        return new ProductResponse(
                product.getId(), product.getName(),
                product.getPrice(), product.getStock());
    }

    public void sell(Long id, int qty) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStock() < qty) {
            throw new IllegalArgumentException("Stock not enough");
        }

        product.setStock(product.getStock() - qty);
        repository.save(product);
    }
}

