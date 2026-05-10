package com.sansys.inventory.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InventoryException extends Exception {
    public InventoryException(String message)
    {
        super(message);
    }
}
