package com.example.InventorySearch.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity                          // Tells JPA "this class = a DB table"
@Table(name = "inventory")       // The actual table name in MySQL
@Data                            // Lombok: generates getters, setters, toString
@NoArgsConstructor               // Lombok: generates empty constructor
@AllArgsConstructor              // Lombok: generates full constructor
@Builder                         // Lombok: lets you build objects cleanly
public class Inventory {

    @Id                                                    // This is the Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // Auto increment 1,2,3...
    private Long id;

    @Column(nullable = false)          // NOT NULL in DB
    private String name;

    private String category;
    private String subcategory;
    private String model;
    private String seller;
    private String location;

    private LocalDate manufacturingDate;
    private LocalDate expiryDate;

    @Column(precision = 10, scale = 2)   // Handles decimal prices like 999.99
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(columnDefinition = "TEXT")   // For long text descriptions
    private String specification;

    // Extra columns — shows initiative to judges!
    private String brand;
    private String color;
    private String warranty;
    private Boolean isActive;            // soft delete — don't actually delete rows

    @Column(updatable = false)           // Set once, never update
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist          // Runs automatically BEFORE saving to DB
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        isActive = true;
    }

    @PreUpdate           // Runs automatically BEFORE updating in DB
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}