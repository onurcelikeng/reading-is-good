package com.onurcelik.readingisgood.order.service;

import com.onurcelik.readingisgood.order.dto.orderitem.OrderItemOutput;
import com.onurcelik.readingisgood.order.entity.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderItemService {

    List<OrderItemOutput> getOrderItems(UUID orderId);

    double getTotalPrice(UUID orderId);

    void save(List<OrderItem> orderItems);
}
