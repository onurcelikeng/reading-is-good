package com.onurcelik.readingisgood.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document("t_order_item")
public class OrderItem {

    @Id
    private UUID id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    private UUID orderId;
    private UUID bookId;
    private int quantity;
    private double unitPrice;

    public OrderItem(UUID orderId, UUID bookId, int quantity, double unitPrice) {
        LocalDateTime now = LocalDateTime.now();

        this.id = UUID.randomUUID();
        this.createDate = now;
        this.modifyDate = now;
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
