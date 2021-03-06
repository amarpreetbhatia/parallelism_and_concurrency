package com.amarpreet.completableFuture;

import com.amarpreet.domain.Product;
import com.amarpreet.service.InventoryService;
import com.amarpreet.service.ProductInfoService;
import com.amarpreet.service.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceCompletableFutureTest {
    private ProductInfoService productInfoService = new ProductInfoService();
    private ReviewService reviewService = new ReviewService();
    private InventoryService inventoryService = new InventoryService();
    ProductServiceCompletableFuture productServiceCompletableFuture =
            new ProductServiceCompletableFuture(productInfoService,reviewService,inventoryService);

    @Test
    void retrieveProductDetails() {
        String productId = "ABC123";
        Product product = productServiceCompletableFuture.retrieveProductDetails(productId);
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetails_approach2(){
        String productId = "ABC123";
        CompletableFuture<Product> product = productServiceCompletableFuture
                .retrieveProductDetails_approach2(productId);
        product.thenAccept(p ->{
            assertNotNull(p);
            assertTrue(p.getProductInfo().getProductOptions().size() > 0);
            assertNotNull(p.getReview());
        });
    }

    @Test
    void retrieveProductDetailsWithInventory(){
        String productId = "ABC123";
        Product product = productServiceCompletableFuture
                .retrieveProductDetailsWithInventory(productId);
        product.getProductInfo()
                .getProductOptions()
                .stream()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }
}