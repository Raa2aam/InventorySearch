package com.example.InventorySearch.specification;



import com.example.InventorySearch.model.Inventory;
import com.example.InventorySearch.dto.InventorySearchRequest;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class InventorySpecification {

    // Private constructor — nobody should create an object of this class
    // It's a utility class, only has static methods
    private InventorySpecification() {}

    public static Specification<Inventory> getFilteredInventory(InventorySearchRequest request) {

        return (root, query, criteriaBuilder) -> {
            // root         = the Inventory table itself
            // query        = the full SELECT query being built
            // criteriaBuilder = the tool to build conditions (like AND, OR, LIKE, >=)

            List<Predicate> predicates = new ArrayList<>();
            // Think of predicates as a list of WHERE conditions
            // We keep adding to this list, then combine them all with AND at the end

            // ── String filters (LIKE for partial match) ──────────────────────

            if (hasValue(request.getName())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),        // lowercase DB value
                                "%" + request.getName().toLowerCase() + "%"     // lowercase user input
                        )
                );
                // SQL: WHERE LOWER(name) LIKE '%samsung%'
                // Case insensitive search — 'Samsung', 'SAMSUNG', 'samsung' all match
            }

            if (hasValue(request.getCategory())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("category")),
                                "%" + request.getCategory().toLowerCase() + "%"
                        )
                );
            }

            if (hasValue(request.getSubcategory())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("subcategory")),
                                "%" + request.getSubcategory().toLowerCase() + "%"
                        )
                );
            }

            if (hasValue(request.getModel())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("model")),
                                "%" + request.getModel().toLowerCase() + "%"
                        )
                );
            }

            if (hasValue(request.getSeller())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("seller")),
                                "%" + request.getSeller().toLowerCase() + "%"
                        )
                );
            }

            if (hasValue(request.getLocation())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("location")),
                                "%" + request.getLocation().toLowerCase() + "%"
                        )
                );
            }

            if (hasValue(request.getBrand())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("brand")),
                                "%" + request.getBrand().toLowerCase() + "%"
                        )
                );
            }

            if (hasValue(request.getColor())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("color")),
                                "%" + request.getColor().toLowerCase() + "%"
                        )
                );
            }

            // ── Price range filters ───────────────────────────────────────────

            if (request.getMinPrice() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice())
                );
                // SQL: WHERE price >= 100
            }

            if (request.getMaxPrice() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getMaxPrice())
                );
                // SQL: WHERE price <= 5000
            }

            // ── Stock range filters ───────────────────────────────────────────

            if (request.getMinStock() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("stock"), request.getMinStock())
                );
            }

            if (request.getMaxStock() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("stock"), request.getMaxStock())
                );
            }

            // ── Manufacturing Date range ──────────────────────────────────────

            if (request.getManufacturingDateFrom() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("manufacturingDate"),
                                request.getManufacturingDateFrom()
                        )
                );
            }

            if (request.getManufacturingDateTo() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("manufacturingDate"),
                                request.getManufacturingDateTo()
                        )
                );
            }

            // ── Expiry Date range ─────────────────────────────────────────────

            if (request.getExpiryDateFrom() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("expiryDate"),
                                request.getExpiryDateFrom()
                        )
                );
            }

            if (request.getExpiryDateTo() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("expiryDate"),
                                request.getExpiryDateTo()
                        )
                );
            }

            // ── Boolean filter ────────────────────────────────────────────────

            if (request.getIsActive() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("isActive"), request.getIsActive())
                );
                // SQL: WHERE is_active = true
            }

            // ── Combine everything with AND ───────────────────────────────────
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            // Takes the whole list and combines:
            // WHERE condition1 AND condition2 AND condition3 ...
        };
    }

    // Helper method — checks if a string is not null and not empty
    private static boolean hasValue(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
