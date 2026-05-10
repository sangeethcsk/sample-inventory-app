package com.sansys.inventory.service;

import com.sansys.inventory.model.Inventory;
import com.sansys.inventory.model.InventorySearchParams;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class InventoryService
{
    private final MongoTemplate mongoTemplate;

    public PageImpl<Inventory> search(InventorySearchParams inventorySearchParams)
    {
        Pageable pageable = PageRequest.of(inventorySearchParams.getPageNumber(), inventorySearchParams.getPageSize());

        Query query = new Query();

        log.info("InventoryService:search - Starting with Building the Criteria");

        if(StringUtils.hasText(inventorySearchParams.getId()))
        {
            query.addCriteria(Criteria.where("id").is(inventorySearchParams.getId()));
        }
        if(StringUtils.hasText(inventorySearchParams.getCategory()))
        {
            query.addCriteria(Criteria.where("category").is(inventorySearchParams.getCategory()));
        }
        if(StringUtils.hasText(inventorySearchParams.getSubcategory()))
        {
            query.addCriteria(Criteria.where("subcategory").is(inventorySearchParams.getSubcategory()));
        }
        if(StringUtils.hasText(inventorySearchParams.getName()))
        {
            query.addCriteria(Criteria.where("name").is(inventorySearchParams.getName()));
        }
        Criteria priceCriteria = Criteria.where("price");

        if (Objects.nonNull(inventorySearchParams.getPriceFrom())) {
            priceCriteria.gte(inventorySearchParams.getPriceFrom());
        }

        if (Objects.nonNull(inventorySearchParams.getPriceTo())) {
            priceCriteria.lte(inventorySearchParams.getPriceTo());
        }

        if(Objects.nonNull(inventorySearchParams.getPriceFrom()) || Objects.nonNull(inventorySearchParams.getPriceTo()))
            query.addCriteria(priceCriteria);

        Criteria mfgDateCriteria = Criteria.where("mfgDate");

        if (Objects.nonNull(inventorySearchParams.getMfgDateFrom())) {
            mfgDateCriteria.gte(inventorySearchParams.getMfgDateFrom());
        }

        if (Objects.nonNull(inventorySearchParams.getMfgDateTo())) {
            mfgDateCriteria.lte(inventorySearchParams.getMfgDateTo());
        }

        if(Objects.nonNull(inventorySearchParams.getMfgDateFrom()) || Objects.nonNull(inventorySearchParams.getMfgDateTo()))
            query.addCriteria(mfgDateCriteria);

        Criteria expDateCriteria = Criteria.where("expDate");

        if (Objects.nonNull(inventorySearchParams.getExpDateFrom())) {
            expDateCriteria.gte(inventorySearchParams.getExpDateFrom());
        }

        if (Objects.nonNull(inventorySearchParams.getExpDateTo())) {
            expDateCriteria.lte(inventorySearchParams.getExpDateTo());
        }

        if(Objects.nonNull(inventorySearchParams.getExpDateFrom()) || Objects.nonNull(inventorySearchParams.getExpDateTo()))
            query.addCriteria(expDateCriteria);

        log.info("InventoryService:search - Completed Building the Criteria");

        List<Inventory> users = mongoTemplate.find(query.with(pageable), Inventory.class);

        long count = mongoTemplate.count(query.skip(0).limit(0), User.class);

        log.info("InventoryService:search - Fetched " + count +  " records");

        return new PageImpl<>(users, pageable, count);
    }
}
