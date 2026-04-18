package com.example.InventorySearch.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data                   // Lombok: getters + setters
@NoArgsConstructor      // Empty constructor
@AllArgsConstructor     // Full constructor
@Builder                // Clean object building
public class InventorySearchRequest {

    // No @NotNull on anything — all fields are OPTIONAL as per problem statement

    private String name;
    private String category;
    private String subcategory;
    private String model;
    private String seller;
    private String location;
    private String brand;
    private String color;
    private Boolean isActive;

    @DecimalMin(value = "0.0", message = "Minimum price cannot be negative")
    private BigDecimal minPrice;

    @DecimalMin(value = "0.0", message = "Maximum price cannot be negative")
    private BigDecimal maxPrice;

    @Min(value = 0, message = "Minimum stock cannot be negative")
    private Integer minStock;

    @Min(value = 0, message = "Maximum stock cannot be negative")
    private Integer maxStock;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)   // Expects format: 2024-01-01
    private LocalDate manufacturingDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate manufacturingDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expiryDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expiryDateTo;
}