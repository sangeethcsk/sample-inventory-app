package com.hcl.hackathon.inventory.controller;

import com.hcl.hackathon.inventory.exception.InvalidDateRangeException;
import com.hcl.hackathon.inventory.model.Inventory;
import com.hcl.hackathon.inventory.model.InventorySearchParams;
import com.hcl.hackathon.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@AllArgsConstructor
@Slf4j
public class InventoryController
{
    private final InventoryService inventoryService;
    @PostMapping("/search")
    public PageImpl<Inventory> search(@Valid @RequestBody InventorySearchParams inventorySearchParams) throws InvalidDateRangeException
    {
        log.info("InventoryController:search - Received request to search Inventory");
        validateInputsAndThrow(inventorySearchParams);
        return inventoryService.search(inventorySearchParams);
    }

    private void validateInputsAndThrow(InventorySearchParams inventorySearchParams) throws InvalidDateRangeException {
        log.info("InventoryController:validateInputsAndThrow - Starting with Input validation");
        boolean mfgDateTo = inventorySearchParams.getMfgDateTo() == null;
        boolean mfgDateFrom = inventorySearchParams.getMfgDateFrom() == null;
        boolean expiryDateTo = inventorySearchParams.getExpDateTo() == null;
        boolean expiryDateFrom = inventorySearchParams.getExpDateFrom() == null;
        if(inventorySearchParams.getPageSize() <= 0)
        {
            log.info("InventoryController:validateInputsAndThrow - Defaulting to page size 100");
            inventorySearchParams.setPageSize(100);
        }
        if(inventorySearchParams.getPageNumber() < 0)
        {
            log.info("InventoryController:validateInputsAndThrow - Defaulting to page Number 0");
            inventorySearchParams.setPageNumber(0);
        }
        if((!mfgDateTo && !mfgDateFrom) && inventorySearchParams.getMfgDateFrom().isAfter(inventorySearchParams.getMfgDateTo()))
        {
            String errorMessage = "Manufacturing date To should be AFTER Manufacturing date From";
            log.error("InventoryController:validateInputsAndThrow - " + errorMessage);
            throw new InvalidDateRangeException(errorMessage);
        }
        if((!expiryDateTo && !expiryDateFrom) && inventorySearchParams.getExpDateTo().isAfter(inventorySearchParams.getExpDateFrom()))
        {
            String errorMessage = "Expiry date To should be AFTER Manufacturing date From";
            log.error("InventoryController:validateInputsAndThrow - " + errorMessage);
            throw new InvalidDateRangeException(errorMessage);
        }

        log.info("InventoryController:validateInputsAndThrow - Completed validation");
    }
}
