package com.example.InventorySearch.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {

    private Long id;
    private String name;
    private String category;
    private String subcategory;
    private String model;
    private String seller;
    private String location;
    private String brand;
    private String color;
    private String warranty;
    private String specification;
    private BigDecimal price;
    private Integer stock;
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
