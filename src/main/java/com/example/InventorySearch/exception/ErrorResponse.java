package com.example.InventorySearch.exception;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    // This is the shape of EVERY error your API returns
    // Consistent error shape = professional API design

    private boolean success;        // always false for errors
    private int status;             // HTTP status code (400, 404, 500)
    private String message;         // human readable message
    private String error;           // error type/code
    private LocalDateTime timestamp;// when did it happen
}
