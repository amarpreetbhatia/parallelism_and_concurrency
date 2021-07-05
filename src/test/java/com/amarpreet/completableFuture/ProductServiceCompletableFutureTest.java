package com.amarpreet.completableFuture;

import com.amarpreet.domain.Product;
import com.amarpreet.service.ProductInfoService;
import com.amarpreet.service.ReviewService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceCompletableFutureTest {
    private ProductInfoService productInfoService = new ProductInfoService();
    private ReviewService reviewService = new ReviewService();
    ProductServiceCompletableFuture productServiceCompletableFuture =
            new ProductServiceCompletableFuture(productInfoService,reviewService);

    @Test
    void retrieveProductDetails() {
        String productId = "ABC123";
        Product product = productServiceCompletableFuture.retrieveProductDetails(productId);
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }
}