package com.onurcelik.readingisgood.order.dto.orderitem;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public final class OrderItemInput {

    private UUID bookId;

    @NotBlank(message = "Miktar bilgisini giriniz.")
    private int amount;
}
