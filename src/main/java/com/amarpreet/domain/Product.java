package com.amarpreet.domain;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @NonNull
    private String productId;
    @NonNull
    private ProductInfo productInfo;
    @NonNull
    private Review review;
}
