package com.example.InventorySearch.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice       // Intercepts ALL exceptions from ALL controllers globally
@Slf4j
public class GlobalExceptionHandler {

    // ── Handles validation errors (@Valid failures) ───────────────────────────
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(BindException ex) {
        // BindException fires when @ModelAttribute validation fails
        // e.g. minPrice = -500 (violates @DecimalMin)

        String message = ex.getBindingResult()
                .getFieldErrors()                           // get all field errors
                .stream()
                .map(FieldError::getDefaultMessage)         // extract the message
                .collect(Collectors.joining(", "));         // join them: "msg1, msg2"

        log.warn("Validation failed: {}", message);

        return ResponseEntity
                .badRequest()                              // HTTP 400
                .body(ErrorResponse.builder()
                        .success(false)
                        .status(400)
                        .error("VALIDATION_ERROR")
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ── Handles wrong type in params ──────────────────────────────────────────
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        // Fires when user sends ?page=abc (page expects Integer)
        // or ?minPrice=hello (minPrice expects BigDecimal)

        String message = String.format(
                "Parameter '%s' has invalid value '%s'",
                ex.getName(),           // which param
                ex.getValue()           // what they sent
        );

        log.warn("Type mismatch: {}", message);

        return ResponseEntity
                .badRequest()           // HTTP 400
                .body(ErrorResponse.builder()
                        .success(false)
                        .status(400)
                        .error("INVALID_PARAMETER_TYPE")
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ── Handles missing required params ───────────────────────────────────────
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(
            MissingServletRequestParameterException ex) {
        // Fires when a required @RequestParam is missing
        // In our case most are optional but good to have

        String message = String.format(
                "Required parameter '%s' is missing",
                ex.getParameterName()
        );

        log.warn("Missing parameter: {}", message);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .success(false)
                        .status(400)
                        .error("MISSING_PARAMETER")
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ── Handles illegal arguments ─────────────────────────────────────────────
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex) {
        // Fires when business logic detects invalid input
        // e.g. minPrice > maxPrice

        log.warn("Illegal argument: {}", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .success(false)
                        .status(400)
                        .error("INVALID_ARGUMENT")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ── Catch ALL other unexpected exceptions ─────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        // Safety net — catches anything we didn't anticipate
        // NEVER expose the actual error message to users in production
        // It might contain DB details, file paths, internal info

        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)   // HTTP 500
                .body(ErrorResponse.builder()
                        .success(false)
                        .status(500)
                        .error("INTERNAL_SERVER_ERROR")
                        .message("Something went wrong. Please try again later.")
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
