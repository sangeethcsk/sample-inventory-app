package com.hcl.hackathon.inventory.controller;

import com.hcl.hackathon.inventory.exception.InvalidDateRangeException;
import com.hcl.hackathon.inventory.model.Inventory;
import com.hcl.hackathon.inventory.model.InventorySearchParams;
import com.hcl.hackathon.inventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearch_ValidInput() throws InvalidDateRangeException {
        // Arrange
        InventorySearchParams params = new InventorySearchParams();
        params.setPageNumber(0);
        params.setPageSize(10);

        Inventory inventory = new Inventory();
        PageImpl<Inventory> mockPage = new PageImpl<>(Collections.singletonList(inventory));
        when(inventoryService.search(params)).thenReturn(mockPage);

        // Act
        PageImpl<Inventory> result = inventoryController.search(params);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(inventoryService, times(1)).search(params);
    }

    @Test
    void testSearch_InvalidInput_ThrowsException() {
        // Arrange
        InventorySearchParams params = new InventorySearchParams();
        params.setMfgDateFrom(LocalDate.of(2025, 5, 10));
        params.setMfgDateTo(LocalDate.of(2025, 1, 10));

        // Act & Assert
        InvalidDateRangeException exception = assertThrows(InvalidDateRangeException.class, () -> inventoryController.search(params));
        assertEquals("Manufacturing date To should be AFTER Manufacturing date From", exception.getMessage());
        verify(inventoryService, never()).search(any());
    }
    @Test
    void testSearch_DefaultPageSizeAndNumber() throws InvalidDateRangeException {
        // Arrange
        InventorySearchParams params = new InventorySearchParams();
        params.setPageNumber(-1);
        params.setPageSize(0);

        Inventory inventory = new Inventory();
        PageImpl<Inventory> mockPage = new PageImpl<>(Collections.singletonList(inventory));
        when(inventoryService.search(params)).thenReturn(mockPage);

        // Act
        PageImpl<Inventory> result = inventoryController.search(params);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(100, params.getPageSize());
        assertEquals(0, params.getPageNumber());
        verify(inventoryService, times(1)).search(params);
    }

    @Test
    void testSearch_NullDates() throws InvalidDateRangeException {
        // Arrange
        InventorySearchParams params = new InventorySearchParams();
        params.setPageNumber(0);
        params.setPageSize(10);

        Inventory inventory = new Inventory();
        PageImpl<Inventory> mockPage = new PageImpl<>(Collections.singletonList(inventory));
        when(inventoryService.search(params)).thenReturn(mockPage);

        // Act
        PageImpl<Inventory> result = inventoryController.search(params);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(inventoryService, times(1)).search(params);
    }

    @Test
    void testSearch_EmptyResult() throws InvalidDateRangeException {
        // Arrange
        InventorySearchParams params = new InventorySearchParams();
        params.setPageNumber(0);
        params.setPageSize(10);

        PageImpl<Inventory> mockPage = new PageImpl<>(Collections.emptyList());
        when(inventoryService.search(params)).thenReturn(mockPage);

        // Act
        PageImpl<Inventory> result = inventoryController.search(params);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        verify(inventoryService, times(1)).search(params);
    }
}