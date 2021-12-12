package com.onurcelik.readingisgood.order.service;

import com.onurcelik.readingisgood.order.dto.order.OrderInput;
import com.onurcelik.readingisgood.order.dto.order.OrderOutput;
import com.onurcelik.readingisgood.order.dto.order.OrderSummaryOutput;
import com.onurcelik.readingisgood.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderSummaryOutput createOrder(OrderInput input);

    List<OrderSummaryOutput> getOrders(LocalDateTime startDate, LocalDateTime endDate);

    List<Order> getOrdersByCustomer(UUID customerId, int pageNumber, int pageSize);

    OrderOutput getOrder(String orderNumber);
}
