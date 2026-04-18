package com.example.InventorySearch.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse<T> {        // <T> means any type — reusable!

    private boolean success;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;
    private List<T> data;
}