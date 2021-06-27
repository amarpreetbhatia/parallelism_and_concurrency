package com.amarpreet.service;

import com.amarpreet.domain.checkout.CartItem;

import static com.amarpreet.util.CommonUtil.delay;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        delay(500);
        if(cartId == 7 || cartId == 9 || cartId == 11){
            return true;
        }
        return false;
    }
}
