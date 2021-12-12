package com.onurcelik.readingisgood.order.dto.order;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryOutput {

    private String orderNumber;
    private String trackingNumber;
    private LocalDateTime orderDate;
}
