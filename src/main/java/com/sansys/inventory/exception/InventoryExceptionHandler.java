package com.sansys.inventory.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class InventoryExceptionHandler {

    @ExceptionHandler(InvalidFilterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFilter(
            InvalidFilterException ex) {

        ErrorResponse error = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, "INV_4001: Invalid Filter inputs");

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDateRange(
            InvalidDateRangeException ex) {

        ErrorResponse error = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, "INV_4002: Invalid Date Range");

        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJson(
            HttpMessageNotReadableException ex)
    {
        String message = "";
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormatException)
        {
            String fieldName = invalidFormatException
                    .getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("."));

            message = String.format(
                    "Invalid value '%s' for field '%s'. Expected format: yyyy-MM-dd",
                    invalidFormatException.getValue(),
                    fieldName
            );
        }

        Map<String, String> error = new HashMap<>();
        error.put("errorMessage", ex.getMostSpecificCause().getMessage());
        error.put("message", message);

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, "INV_5000: Unexpected error occurred");

        return ResponseEntity.internalServerError().body(error);
    }

}