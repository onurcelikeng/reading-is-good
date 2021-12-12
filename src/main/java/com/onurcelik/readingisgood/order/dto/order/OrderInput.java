package com.onurcelik.readingisgood.order.dto.order;

import com.onurcelik.readingisgood.order.dto.orderitem.OrderItemInput;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderInput {

    private UUID customerId;

    private UUID addressId;

    //@NotBlank(message = "Kitap bilgilerini giriniz.")
    private List<OrderItemInput> orderItems;
}
