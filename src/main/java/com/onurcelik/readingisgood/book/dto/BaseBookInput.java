package com.onurcelik.readingisgood.book.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class BaseBookInput {

    @Min(value = 1, message = "Minimum ücret değerini aşmaktasınız.")
    private double price;

    @Positive(message = "Geçerli stok sayısı giriniz.")
    private int stock;
}
