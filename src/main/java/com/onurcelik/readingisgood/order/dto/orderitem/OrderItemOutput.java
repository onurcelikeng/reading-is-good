package com.onurcelik.readingisgood.order.dto.orderitem;

import com.onurcelik.readingisgood.book.dto.BookOutput;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemOutput implements Serializable {

    private BookOutput book;
    private int quantity;
    private double unitPrice;
}
