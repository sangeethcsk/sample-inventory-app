package com.sansys.inventory.repository;

import com.sansys.inventory.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//FUTURE USAGE -- Optional in case we need simpler Implementations
@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    List<Inventory> findByIdAndNameAndCategoryAndSubcategory(String id, String name, String category, String subcategory);
}
