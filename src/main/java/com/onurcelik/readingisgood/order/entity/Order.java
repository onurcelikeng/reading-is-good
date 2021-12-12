package com.onurcelik.readingisgood.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document("t_order")
public final class Order {

    @Id
    private UUID id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    private UUID customerId;
    private UUID addressId;

    @Indexed
    private String orderNumber;
    private String trackingNumber;
    private double totalPrice;

    public Order(UUID customerId, UUID addressId, String orderNumber) {
        LocalDateTime now = LocalDateTime.now();

        this.id = UUID.randomUUID();
        this.createDate = now;
        this.modifyDate = now;
        this.customerId = customerId;
        this.addressId = addressId;
        this.orderNumber = orderNumber;
    }
}
