package com.amarpreet.domain.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    private Integer cartId;
    private List<CartItem> cartItemList;
}
