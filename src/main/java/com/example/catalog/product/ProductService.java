package com.example.catalog.product;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) { this.repository = repository; }

    public Product create(ProductRequest request) {
        return repository.save(new Product(request.name(), request.category(),
                request.price(), request.description()));
    }

    @Transactional(readOnly = true)
    public Page<Product> search(String category, BigDecimal minPrice, BigDecimal maxPrice,
                                int page, int size) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("minPrice must be less than or equal to maxPrice");
        }
        int safeSize = Math.min(Math.max(size, 1), 100);
        int safePage = Math.max(page, 0);
        Pageable pageable = PageRequest.of(safePage, safeSize,
                Sort.by(Sort.Direction.ASC, "id"));
        return repository.search(category, minPrice, maxPrice, pageable);
    }

    @Transactional(readOnly = true)
    public Product get(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product update(UUID id, ProductRequest request) {
        Product product = get(id);
        product.update(request.name(), request.category(), request.price(), request.description());
        return repository.save(product);
    }

    public void delete(UUID id) {
        Product product = get(id);
        repository.delete(product);
    }
}
