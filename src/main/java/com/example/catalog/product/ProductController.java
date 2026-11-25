package com.example.catalog.product;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductRequest request) {
        return service.create(request);
    }

    @GetMapping
    public Page<Product> search(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return service.search(category, minPrice, maxPrice, page, size);
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable UUID id) { return service.get(id); }

    @PutMapping("/{id}")
    public Product update(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) { service.delete(id); }
}
