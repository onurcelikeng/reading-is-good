package com.onurcelik.readingisgood.book.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public final class BookDetailOutput extends BookOutput {

    private UUID bookId;
    private double price;
    private int stock;
}
