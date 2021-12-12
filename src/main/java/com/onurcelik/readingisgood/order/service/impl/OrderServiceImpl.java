package com.onurcelik.readingisgood.order.service.impl;

import com.onurcelik.readingisgood.address.entity.Address;
import com.onurcelik.readingisgood.address.service.AddressService;
import com.onurcelik.readingisgood.book.entity.Book;
import com.onurcelik.readingisgood.book.service.BookService;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.customer.entity.Customer;
import com.onurcelik.readingisgood.customer.service.CustomerService;
import com.onurcelik.readingisgood.order.dto.order.OrderInput;
import com.onurcelik.readingisgood.order.dto.order.OrderOutput;
import com.onurcelik.readingisgood.order.dto.order.OrderSummaryOutput;
import com.onurcelik.readingisgood.order.dto.orderitem.OrderItemInput;
import com.onurcelik.readingisgood.order.entity.Order;
import com.onurcelik.readingisgood.order.entity.OrderItem;
import com.onurcelik.readingisgood.order.repository.OrderRepository;
import com.onurcelik.readingisgood.order.service.OrderItemService;
import com.onurcelik.readingisgood.order.service.OrderService;
import com.onurcelik.readingisgood.order.util.OrderNumberUtil;
import com.onurcelik.readingisgood.shipping.dto.ShippingOutput;
import com.onurcelik.readingisgood.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final AddressService addressService;
    private final OrderItemService orderItemService;
    private final BookService bookService;
    private final ShippingService shipmentService;

    @Override
    @Transactional
    public OrderSummaryOutput createOrder(OrderInput input) {
        Customer customer = customerService.getCustomer(input.getCustomerId());
        Address address = addressService.getAddressByCustomer(input.getAddressId(), customer.getId());

        Order order = new Order(customer.getId(), address.getAddressId(), OrderNumberUtil.generate());
        List<OrderItem> orderItems = new ArrayList<>();
        List<Book> books = new ArrayList<>();
        double totalPrice = 0;

        for (OrderItemInput orderItemInput : input.getOrderItems()) {
            Book book = bookService.getBookById(orderItemInput.getBookId());

            // stock controls
            if(book.getStock() < 1) {
                throw new BusinessException(book.getName() + " isimli kitap stoklarda hiç yoktur.");
            }
            if(book.getStock() < orderItemInput.getAmount()) {
                throw new BusinessException(book.getName() + " isimli kitaptan yeterli stock bulunmamaktadır.");
            }

            // prepare created order item
            orderItems.add(new OrderItem(order.getId(), book.getId(), orderItemInput.getAmount(), book.getPrice()));

            totalPrice += orderItemInput.getAmount() * book.getPrice();

            // prepare update stock
            book.setStock(book.getStock() - orderItemInput.getAmount());
            books.add(book);
        }

        // get shipping
        ShippingOutput shipping = shipmentService.getShipping();

        // calculate total price
        totalPrice += shipping.getShippingCost();
        order.setTotalPrice(totalPrice);

        order.setTrackingNumber(shipping.getTrackingNumber());

        orderRepository.save(order);
        orderItemService.save(orderItems);
        log.info("Sipraiş verildi.");

        bookService.saveAll(books);
        log.info("Stoklar güncellendi.");

        return new OrderSummaryOutput(order.getOrderNumber(), shipping.getTrackingNumber(), order.getCreateDate());
    }

    @Override
    public List<OrderSummaryOutput> getOrders(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate == null) {
            throw new BusinessException("Başlangıç tarihi boş olamaz!");
        } else if(endDate == null) {
            throw new BusinessException("Bitiş tarihi boş olamaz!");
        }

        if(startDate.isAfter(endDate)) {
            throw new BusinessException("Başlangıç tarihi bitiş tarihinden daha büyük olamaz!");
        }

        List<OrderSummaryOutput> orderSummaryOutputs = new ArrayList<>();

        List<Order> orders = orderRepository.findAllByCreateDateBetween(startDate, endDate);
        for(Order order : orders) {
            orderSummaryOutputs.add(OrderSummaryOutput.builder()
                    .orderNumber(order.getOrderNumber())
                    .trackingNumber(order.getTrackingNumber())
                    .orderDate(order.getCreateDate())
                    .build());
        }
        return orderSummaryOutputs;
    }

    @Override
    public List<Order> getOrdersByCustomer(UUID customerId, int pageNumber, int pageSize) {
        Page<Order> orderPage = orderRepository.findAllByCustomerId(customerId, PageRequest.of(pageNumber, pageSize));
        if(!orderPage.hasContent()) {
            throw new BusinessException("Order not found!");
        }
        return orderPage.getContent();
    }

    @Override
    public OrderOutput getOrder(String orderNumber) {
        Order order = orderRepository.findOrderByOrderNumber(orderNumber);
        if(order == null) {
            throw new BusinessException("Order not found!");
        }

        return OrderOutput.builder()
                .orderNumber(order.getOrderNumber())
                .orderDate(order.getCreateDate())
                .totalPrice(orderItemService.getTotalPrice(order.getId()))
                .customer(customerService.getCustomerSummary(order.getCustomerId()))
                .address(addressService.getAddress(order.getAddressId()))
                .orderItems(orderItemService.getOrderItems(order.getId()))
                .build();
    }
}
