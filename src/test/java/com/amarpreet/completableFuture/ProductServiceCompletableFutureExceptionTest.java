package com.amarpreet.completableFuture;

import com.amarpreet.domain.Product;
import com.amarpreet.service.InventoryService;
import com.amarpreet.service.ProductInfoService;
import com.amarpreet.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceCompletableFutureExceptionTest {

    @Mock
    ReviewService reviewService = mock(ReviewService.class);
    @Mock
    ProductInfoService productInfoService = mock(ProductInfoService.class);
    @Mock
    InventoryService inventoryService = mock(InventoryService.class);
    @InjectMocks
    ProductServiceCompletableFuture productServiceCompletableFuture;

    @Test
    void retrieveProductDetails_approach2(){
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenThrow(new RuntimeException(" Throw Review Exception"));

        Product product = productServiceCompletableFuture
                .retrieveProductDetails_approach2(productId).join();

        assertNotNull(product);
        assertNotNull(product.getReview());
        assertEquals(0,product.getReview().getNoOfReviews());
    }

    @Test
    void retrieveProductDetails_approach2_Throw_UnHandled_Exception(){
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenThrow(new RuntimeException(" Throw Product Info Exception"));
//        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();
//        when(reviewService.retrieveReviews(any())).thenCallRealMethod();

        Assertions.assertThrows(RuntimeException.class,() -> productServiceCompletableFuture
                .retrieveProductDetails_approach2(productId).join());

    }
}
