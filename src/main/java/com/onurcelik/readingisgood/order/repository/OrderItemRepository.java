package com.onurcelik.readingisgood.order.repository;

import com.onurcelik.readingisgood.order.entity.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends MongoRepository<OrderItem, UUID> {

    List<OrderItem> findAllOrderItemByOrderId(UUID orderId);
}
