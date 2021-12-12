package com.onurcelik.readingisgood.order.service.impl;

import com.onurcelik.readingisgood.book.entity.Book;
import com.onurcelik.readingisgood.book.repository.BookRepository;
import com.onurcelik.readingisgood.book.service.BookService;
import com.onurcelik.readingisgood.book.service.impl.BookServiceImpl;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.order.dto.orderitem.OrderItemOutput;
import com.onurcelik.readingisgood.order.entity.OrderItem;
import com.onurcelik.readingisgood.order.repository.OrderItemRepository;
import com.onurcelik.readingisgood.order.service.OrderItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Or;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @Mock
    private OrderItemRepository mockOrderItemRepository;

    @Mock
    private BookRepository mockBookRepository;

    private BookService bookService;

    private OrderItemService orderItemService;

    @BeforeEach
    void setup() {
        this.bookService = new BookServiceImpl(mockBookRepository);
        this.orderItemService = new OrderItemServiceImpl(mockOrderItemRepository, bookService);
    }

    @Test
    void testGetOrderItems_valid_returnList() {
        UUID orderId = UUID.randomUUID();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(orderId, UUID.randomUUID(), 2, 10));

        when(mockOrderItemRepository.findAllOrderItemByOrderId(orderId)).thenReturn(orderItems);
        when(mockBookRepository.findBookById(any())).thenReturn(new Book("test_name", "test_author", "test_publisher", 25, 10));

        List<OrderItemOutput> response = orderItemService.getOrderItems(orderId);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    void testGetOrderItems_orderItemEmpty_throwsException() {
        UUID orderId = UUID.randomUUID();

        when(mockOrderItemRepository.findAllOrderItemByOrderId(orderId)).thenReturn(new ArrayList<>());

        Assertions.assertThrows(BusinessException.class, () -> orderItemService.getOrderItems(orderId));
    }

    @Test
    void testGetTotalPrice_valid_returnDouble() {
        UUID orderId = UUID.randomUUID();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(orderId, UUID.randomUUID(), 2, 10));
        orderItems.add(new OrderItem(orderId, UUID.randomUUID(), 1, 25));

        when(mockOrderItemRepository.findAllOrderItemByOrderId(orderId)).thenReturn(orderItems);

        double response = orderItemService.getTotalPrice(orderId);
        Assertions.assertEquals(45, response);
    }

    @Test
    void testGetTotalPrice_orderItemEmpty_throwsException() {
        UUID orderId = UUID.randomUUID();

        when(mockOrderItemRepository.findAllOrderItemByOrderId(orderId)).thenReturn(new ArrayList<>());

        Assertions.assertThrows(BusinessException.class, () -> orderItemService.getTotalPrice(orderId));
    }
}