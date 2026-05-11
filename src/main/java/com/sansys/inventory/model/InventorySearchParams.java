package com.sansys.inventory.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class InventorySearchParams {
    private String id;
    private String name;
    private String category;
    private String subcategory;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "Manufacturing date should be in the Past")
    private LocalDate mfgDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "Manufacturing date should be in the Past")
    private LocalDate mfgDateTo;

    @Future(message = "Expiry date should be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expDateFrom;

    @Future(message = "Expiry date should be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expDateTo;

    @Min(value = 0, message = "priceFrom cannot be negative")
    private Long priceFrom;

    @Min(value = 0, message = "priceTo cannot be negative")
    private Long priceTo;

    private int pageNumber;
    private int pageSize;
}
