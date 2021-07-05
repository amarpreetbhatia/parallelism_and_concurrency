package com.amarpreet.completableFuture;

import com.amarpreet.domain.Product;
import com.amarpreet.domain.ProductInfo;
import com.amarpreet.domain.Review;
import com.amarpreet.service.ProductInfoService;
import com.amarpreet.service.ReviewService;

import java.util.concurrent.CompletableFuture;

import static com.amarpreet.util.CommonUtil.stopWatch;
import static com.amarpreet.util.LoggerUtil.mylog;

class ProductServiceCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> completableFutureProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> completableFutureReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product =
                completableFutureProductInfo.thenCombine(completableFutureReview,
                        (p,r) -> new Product(productId, p, r)).join();
        stopWatch.stop();
        mylog("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> completableFutureProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> completableFutureReview = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        CompletableFuture<Product> product =
                completableFutureProductInfo.thenCombine(completableFutureReview,
                        (p,r) -> new Product(productId, p, r));
        stopWatch.stop();
        mylog("Total Time Taken : "+ stopWatch.getTime());
        return product;
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
