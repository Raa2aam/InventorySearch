package com.example.InventorySearch.controller;


import com.example.InventorySearch.dto.*;
import com.example.InventorySearch.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController                           // Tells Spring: this handles REST requests
@RequestMapping("/api/v1/inventory")      // Base URL for all endpoints in this class
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Inventory Search API",       // Swagger UI label
        description = "Search and filter inventory items")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/search")               // HTTP GET /api/v1/inventory/search
    @Operation(
            summary = "Search Inventory",
            description = "Search inventory with optional filters. All parameters are optional and combined with AND logic."
    )
    public ResponseEntity<PagedResponse<InventoryResponse>> searchInventory(

            // All search filters — mapped from query params
            @Valid @ModelAttribute InventorySearchRequest request,
            // @Valid triggers validation annotations on the DTO
            // @ModelAttribute maps ?name=X&category=Y into the object automatically

            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,
            // defaultValue means if user doesn't send it → use 0

            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Field to sort by")
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(description = "Sort direction: asc or desc")
            @RequestParam(defaultValue = "asc") String sortDir) {

        log.info("Received search request - page: {}, size: {}, sortBy: {}, sortDir: {}",
                page, size, sortBy, sortDir);

        PagedResponse<InventoryResponse> response =
                inventoryService.searchInventory(request, page, size, sortBy, sortDir);

        return ResponseEntity.ok(response);
        // ResponseEntity.ok() = HTTP 200 status + the response body
    }
}