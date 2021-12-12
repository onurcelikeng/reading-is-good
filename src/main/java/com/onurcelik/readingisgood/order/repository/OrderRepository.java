package com.onurcelik.readingisgood.order.repository;

import com.onurcelik.readingisgood.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {

    List<Order> findAllByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Order> findAllByCustomerId(UUID customerId, Pageable pageable);

    Order findOrderByOrderNumber(String orderNumber);
}
