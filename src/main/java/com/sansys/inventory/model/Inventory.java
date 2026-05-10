package com.sansys.inventory.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document(collection = "inventory")
public class Inventory
{
    @Id
    private String id;
    private String name;
    private String category;
    private String subcategory;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "Manufacturing date should be in the Past")
    private LocalDate mfgDate;
    @Future(message = "Expiry date should be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expDate;
    private String specification;
    private BigDecimal price;
    private long stock;
    private String model;
    private String seller;
    private String location;
}
