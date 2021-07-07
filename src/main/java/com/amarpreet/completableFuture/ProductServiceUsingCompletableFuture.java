package com.amarpreet.completableFuture;

import com.amarpreet.domain.*;
import com.amarpreet.service.InventoryService;
import com.amarpreet.service.ProductInfoService;
import com.amarpreet.service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.amarpreet.util.CommonUtil.stopWatch;
import static com.amarpreet.util.LoggerUtil.mylog;

class ProductServiceCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }
    public ProductServiceCompletableFuture(ProductInfoService productInfoService,
                                           ReviewService reviewService,
                                           InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetails(String productId) {
        long startTime = System.currentTimeMillis();
        CompletableFuture<ProductInfo> completableFutureProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> completableFutureReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product =
                completableFutureProductInfo.thenCombine(completableFutureReview,
                        (p,r) -> new Product(productId, p, r)).join();
        long endTime =  System.currentTimeMillis();
        mylog("Total Time Taken : "+ (endTime - startTime));
        return product;
    }

    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {
        long startTime = System.currentTimeMillis();
        CompletableFuture<ProductInfo> completableFutureProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> completableFutureReview = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId))
                .exceptionally((e) ->{
                    System.out.println("Review Service exception:: " + e.getMessage());
                    return Review.builder().noOfReviews(0).overallRating(0.0).build();
                });

        CompletableFuture<Product> product =
                completableFutureProductInfo
                        .thenCombine(completableFutureReview,
                                (p,r) -> new Product(productId, p, r))
                        .whenComplete((product1, ex) -> {
                            System.out.println(" When Complete throws exception for " + product1
                                    + " where exception message is " + ex.getMessage());
                        });

        long endTime = System.currentTimeMillis();
        mylog("Total Time Taken : "+ (endTime - startTime));
        return product;
    }

    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> completableFutureProductInfo =
                CompletableFuture
                        .supplyAsync(() -> productInfoService
                                .retrieveProductInfo(productId))
                        .thenApply(productInfo -> {
                            productInfo.setProductOptions(updateInventory(productInfo));
                            return productInfo;
                        });


        CompletableFuture<Review> completableFutureReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product =
                completableFutureProductInfo.thenCombine(completableFutureReview,
                        (p,r) -> new Product(productId, p, r)).join();
        stopWatch.stop();
        mylog("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
       List<ProductOption> listOfProductOptions = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    Inventory inventory = inventoryService.retrieveInventory(productOption);
                    productOption.setInventory(inventory);
                    return productOption;
                }).collect(Collectors.toList());
       return listOfProductOptions;
    }

    private List<ProductOption> updateInventoryWithBetterPerformance(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> listOfProductOptions = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                   return CompletableFuture
                            .supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                            .thenApply(inventory -> {
                        productOption.setInventory(inventory);
                        return productOption;
                    });
                })
                .collect(Collectors.toList());
        return listOfProductOptions.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args){
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceCompletableFuture productService = new ProductServiceCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        mylog("Product is " + product);
    }
}
