package com.amarpreet.service;

import com.amarpreet.domain.checkout.Cart;
import com.amarpreet.domain.checkout.CheckoutResponse;
import com.amarpreet.domain.checkout.CheckoutStatus;
import com.amarpreet.forkjoin.DataSet;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void no_of_cores(){
        System.out.println("no of cores :::\t" + Runtime.getRuntime().availableProcessors());
    }

    @Test
    void checkout() {
        //given
        Cart cart = DataSet.createCart(6);
        //when
       CheckoutResponse response = checkoutService.checkout(cart);
        //then
        assertEquals(CheckoutStatus.SUCCESS,response.getCheckoutStatus());
        assertTrue(response.getFinalPrice() > 0.0);
    }

    @Test
    void checkout_with_25_items(){
        System.out.println("Number of Parallelism:::->" + ForkJoinPool.getCommonPoolParallelism());
        // *** Override ForkJoin Parallel stream
        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");
            //given
            Cart cart = DataSet.createCart(25);
            //when
            long startTime = System.currentTimeMillis();
            CheckoutResponse response = checkoutService.checkout(cart);
            long endTime = System.currentTimeMillis();
            //then
            System.out.println("Time Taken :::\t" + (endTime - startTime));
            assertEquals(CheckoutStatus.FAILURE,response.getCheckoutStatus());
    }
}