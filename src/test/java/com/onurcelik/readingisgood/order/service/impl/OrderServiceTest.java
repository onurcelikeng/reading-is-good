package com.onurcelik.readingisgood.order.service.impl;

import com.onurcelik.readingisgood.address.entity.Address;
import com.onurcelik.readingisgood.address.repository.AddressRepository;
import com.onurcelik.readingisgood.address.service.AddressService;
import com.onurcelik.readingisgood.address.service.impl.AddressServiceImpl;
import com.onurcelik.readingisgood.book.entity.Book;
import com.onurcelik.readingisgood.book.repository.BookRepository;
import com.onurcelik.readingisgood.book.service.BookService;
import com.onurcelik.readingisgood.book.service.impl.BookServiceImpl;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.customer.entity.Customer;
import com.onurcelik.readingisgood.customer.repository.CustomerRepository;
import com.onurcelik.readingisgood.customer.service.CustomerService;
import com.onurcelik.readingisgood.customer.service.impl.CustomerServiceImpl;
import com.onurcelik.readingisgood.order.dto.order.OrderOutput;
import com.onurcelik.readingisgood.order.entity.Order;
import com.onurcelik.readingisgood.order.entity.OrderItem;
import com.onurcelik.readingisgood.order.repository.OrderItemRepository;
import com.onurcelik.readingisgood.order.repository.OrderRepository;
import com.onurcelik.readingisgood.order.service.OrderItemService;
import com.onurcelik.readingisgood.order.service.OrderService;
import com.onurcelik.readingisgood.shipping.service.ShippingService;
import com.onurcelik.readingisgood.shipping.service.impl.ShippingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private OrderItemRepository orderItemRepository;


    private OrderService orderService;

    private OrderItemService orderItemService;

    private CustomerService customerService;

    private AddressService addressService;

    private BookService bookService;

    private ShippingService shipmentService;

    @BeforeEach
    void setup() {
        customerService = new CustomerServiceImpl(customerRepository);
        addressService = new AddressServiceImpl(addressRepository);
        bookService = new BookServiceImpl(bookRepository);
        orderItemService = new OrderItemServiceImpl(orderItemRepository, bookService);
        shipmentService = new ShippingServiceImpl();
        orderService = new OrderServiceImpl(mockOrderRepository, customerService, addressService, orderItemService, bookService, shipmentService);
    }

    @Test
    void testGetOrder_valid_returnObject() {
        UUID orderId = UUID.randomUUID();
        String orderNumber = "123123212";

        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), orderNumber);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(orderId, UUID.randomUUID(), 2, 10));
        orderItems.add(new OrderItem(orderId, UUID.randomUUID(), 1, 25));

        Customer customer = new Customer("onur", null, "celik", "test@gmail.com", "5064328877");

        Address address = new Address(UUID.randomUUID(), "ASD", "ASD", "ASD", "ASD","ASD","ASD", "ASD");

        Book book = new Book("test_name_1", "test_author_1", "test_publisher_1", 25, 10);

        when(bookRepository.findBookById(any())).thenReturn(book);
        when(addressRepository.findAddressByAddressId(any())).thenReturn(address);
        when(customerRepository.findCustomerById(any())).thenReturn(customer);
        when(orderItemRepository.findAllOrderItemByOrderId(any())).thenReturn(orderItems);
        when(mockOrderRepository.findOrderByOrderNumber(orderNumber)).thenReturn(order);

        OrderOutput response = orderService.getOrder(orderNumber);
        Assertions.assertEquals(orderNumber, response.getOrderNumber());
    }

    @Test
    void testGetOrder_orderNotFound_throwsException() {
        String orderNumber = "123123212";

        when(mockOrderRepository.findOrderByOrderNumber(orderNumber)).thenReturn(null);

        Assertions.assertThrows(BusinessException.class, () -> orderService.getOrder(orderNumber));
    }
}