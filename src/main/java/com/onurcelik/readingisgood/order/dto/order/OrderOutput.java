package com.onurcelik.readingisgood.order.dto.order;

import com.onurcelik.readingisgood.address.dto.AddressOutput;
import com.onurcelik.readingisgood.customer.dto.CustomerOutput;
import com.onurcelik.readingisgood.order.dto.orderitem.OrderItemOutput;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOutput implements Serializable {

    private String orderNumber;
    private double totalPrice;
    private LocalDateTime orderDate;
    private CustomerOutput customer;
    private AddressOutput address;
    private List<OrderItemOutput> orderItems;
}
