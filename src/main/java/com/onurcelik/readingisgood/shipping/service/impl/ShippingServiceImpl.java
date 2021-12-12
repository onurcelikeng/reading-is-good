package com.onurcelik.readingisgood.shipping.service.impl;

import com.onurcelik.readingisgood.shipping.dto.ShippingOutput;
import com.onurcelik.readingisgood.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    @Override
    public ShippingOutput getShipping() {
        return ShippingOutput.builder()
                .shippingCost(calculateShippingPrice())
                .trackingNumber(generateTrackingNumber())
                .build();
    }

    private String generateTrackingNumber() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(12121212));
    }

    private double calculateShippingPrice() {
        return 12.8;
    }
}
