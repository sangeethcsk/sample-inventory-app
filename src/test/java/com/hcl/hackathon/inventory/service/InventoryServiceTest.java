package com.hcl.hackathon.inventory.service;

import com.hcl.hackathon.inventory.model.Inventory;
import com.hcl.hackathon.inventory.model.InventorySearchParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearch_WithValidParams_ReturnsResults() {
        // Arrange
        InventorySearchParams params = new InventorySearchParams();
        params.setId("123");
        params.setCategory("Electronics");
        params.setSubcategory("Mobile");
        params.setName("iPhone");
        params.setPageNumber(0);
        params.setPageSize(10);

        Inventory inventory = new Inventory();
        List<Inventory> inventoryList = Collections.singletonList(inventory);
        Query expectedQuery = new Query();
        PageRequest pageable = PageRequest.of(0, 10);

        when(mongoTemplate.find(any(Query.class), eq(Inventory.class))).thenReturn(inventoryList);
        when(mongoTemplate.count(any(Query.class), eq(Inventory.class))).thenReturn(1L);

        // Act
        PageImpl<Inventory> result = inventoryService.search(params);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Inventory.class));
    }

    @Test
    void testSearch_WithNoResults_ReturnsEmptyPage() {
        // Arrange
        InventorySearchParams params = new InventorySearchParams();
        params.setPageNumber(0);
        params.setPageSize(10);

        when(mongoTemplate.find(any(Query.class), eq(Inventory.class))).thenReturn(Collections.emptyList());
        when(mongoTemplate.count(any(Query.class), eq(Inventory.class))).thenReturn(0L);

        // Act
        PageImpl<Inventory> result = inventoryService.search(params);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Inventory.class));
    }
}