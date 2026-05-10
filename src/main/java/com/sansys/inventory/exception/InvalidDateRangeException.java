package com.sansys.inventory.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidDateRangeException extends Exception
{
    public InvalidDateRangeException(String message)
    {
        super(message);
    }
}
