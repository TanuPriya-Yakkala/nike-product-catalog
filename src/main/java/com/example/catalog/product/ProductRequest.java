package com.example.catalog.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank @Size(max = 160) String name,
    @NotBlank @Size(max = 80) String category,
    @NotNull @DecimalMin(value = "0.00") @Digits(integer = 10, fraction = 2) BigDecimal price,
    @Size(max = 1000) String description
) {}
