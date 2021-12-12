package com.onurcelik.readingisgood.order.service.impl;

import com.onurcelik.readingisgood.book.dto.BookOutput;
import com.onurcelik.readingisgood.book.service.BookService;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.order.dto.orderitem.OrderItemOutput;
import com.onurcelik.readingisgood.order.entity.OrderItem;
import com.onurcelik.readingisgood.order.repository.OrderItemRepository;
import com.onurcelik.readingisgood.order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final BookService bookService;

    @Override
    public List<OrderItemOutput> getOrderItems(UUID orderId){
        List<OrderItem> orderItems = orderItemRepository.findAllOrderItemByOrderId(orderId);
        if(orderItems.isEmpty()) {
            throw new BusinessException("Order Item not found!");
        }

        List<OrderItemOutput> orderItemOutputs = new ArrayList<>();
        for(OrderItem orderItem : orderItems) {
            BookOutput bookOutput = bookService.getBook(orderItem.getBookId());
            orderItemOutputs.add(OrderItemOutput.builder()
                    .book(bookOutput)
                    .quantity(orderItem.getQuantity())
                    .unitPrice(orderItem.getUnitPrice())
                    .build());
        }
        return orderItemOutputs;
    }

    @Override
    public double getTotalPrice(UUID orderId) {
        List<OrderItem> orderItems = orderItemRepository.findAllOrderItemByOrderId(orderId);
        if(orderItems.isEmpty()) {
            throw new BusinessException("Order Item not found!");
        }
        return orderItems.stream().mapToDouble(item -> item.getUnitPrice() * item.getQuantity()).sum();
    }

    @Override
    public void save(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }
}
