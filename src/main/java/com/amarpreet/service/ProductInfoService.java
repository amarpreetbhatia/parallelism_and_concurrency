package com.amarpreet.service;

import com.amarpreet.domain.ProductInfo;
import com.amarpreet.domain.ProductOption;

import java.util.List;

import static com.amarpreet.util.CommonUtil.delay;

public class ProductInfoService {
    public ProductInfo retrieveProductInfo(String productId) {
        delay(1000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99));
        return ProductInfo.builder().productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
