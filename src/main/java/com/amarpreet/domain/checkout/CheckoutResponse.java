package com.amarpreet.domain.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheckoutResponse {
    CheckoutStatus checkoutStatus;
    List<CartItem> errorList = new ArrayList<>();
    double finalPrice = 0.0;

    public CheckoutResponse(CheckoutStatus checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }

    public CheckoutResponse(CheckoutStatus checkoutStatus, List<CartItem> errorList) {
        this.checkoutStatus = checkoutStatus;
        this.errorList = errorList;
    }

    public CheckoutResponse(CheckoutStatus checkoutStatus, double finalPrice) {
        this.checkoutStatus = checkoutStatus;
        this.finalPrice = finalPrice;
    }
}
