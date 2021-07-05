package com.amarpreet.service;

import com.amarpreet.domain.Inventory;
import com.amarpreet.domain.ProductOption;

import static com.amarpreet.util.CommonUtil.delay;

public class InventoryService {
    public Inventory retrieveInventory(ProductOption productOption){
        delay(500);
        return Inventory.builder().count(2).build();
    }
}
