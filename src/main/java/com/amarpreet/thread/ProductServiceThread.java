package com.amarpreet.thread;

import com.amarpreet.domain.Product;
import com.amarpreet.domain.ProductInfo;
import com.amarpreet.domain.Review;
import com.amarpreet.service.ProductInfoService;
import com.amarpreet.service.ReviewService;

import static com.amarpreet.util.CommonUtil.stopWatch;
import static com.amarpreet.util.LoggerUtil.log;

public class ProductServiceThread {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();
        ProductInfoServiceRunnable productInfoRunnable = new ProductInfoServiceRunnable(productId);
        ReviewServiceRunnable reviewRunnable = new ReviewServiceRunnable(productId);

        Thread productInfoThread = new Thread(productInfoRunnable);
        Thread reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        // Join, to wait the thread finish and return is available.
        productInfoThread.join();
        reviewThread.join();

        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review =  reviewRunnable.getReview();

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceThread productService = new ProductServiceThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
    }

    private class ProductInfoServiceRunnable implements Runnable {
        private String productId;
        private ProductInfo productInfo;

        public ProductInfoServiceRunnable(String productId) {
            this.productId = productId;
        }

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        @Override
        public void run() {
            productInfo = productInfoService.retrieveProductInfo(productId);
        }
    }

    private class ReviewServiceRunnable implements Runnable{
        private String productId;
        private Review review;
        public ReviewServiceRunnable(String productId) {
            this.productId = productId;
        }

        public Review getReview() {
            return review;
        }

        @Override
        public void run() {
            review = reviewService.retrieveReviews(productId);
        }
    }
}
