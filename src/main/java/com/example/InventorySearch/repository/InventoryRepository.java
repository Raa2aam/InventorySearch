package com.example.InventorySearch.repository;

import com.example.InventorySearch.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends
        JpaRepository<Inventory, Long>,           // Basic CRUD operations
        JpaSpecificationExecutor<Inventory> {     // Dynamic filter/search support
}