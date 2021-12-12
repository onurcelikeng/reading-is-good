package com.onurcelik.readingisgood.shipping.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ShippingOutput implements Serializable {

    private String shippingCompany;
    private double shippingCost;
    private String trackingNumber;
}
