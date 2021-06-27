package com.amarpreet.service;

import com.amarpreet.domain.checkout.Cart;
import com.amarpreet.domain.checkout.CartItem;
import com.amarpreet.domain.checkout.CheckoutResponse;
import com.amarpreet.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.amarpreet.util.CommonUtil.startTimer;
import static com.amarpreet.util.CommonUtil.timeTaken;

public class CheckoutService {

    private PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart){
        startTimer();
        List<CartItem> priceValidationList = cart.getCartItemList()
                .stream()
                .map(cartItem -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        if(priceValidationList.size() > 0){
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }
        timeTaken();
        return new CheckoutResponse(CheckoutStatus.SUCCESS);
    }

}
