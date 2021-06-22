package com.amarpreet.service;

import com.amarpreet.domain.Review;

import static com.amarpreet.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}
