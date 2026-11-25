package com.example.catalog.product;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_product_category_price_id", columnList = "category, price, id"),
    @Index(name = "idx_product_price_id", columnList = "price, id")
})
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(nullable = false, length = 80)
    private String category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected Product() {}

    public Product(String name, String category, BigDecimal price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() { updatedAt = Instant.now(); }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void update(String name, String category, BigDecimal price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }
}
