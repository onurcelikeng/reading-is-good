package com.onurcelik.readingisgood.book.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public final class BookAddInput extends BaseBookInput implements Serializable {

    @NotBlank(message = "İsim bilgisini giriniz.")
    private String name;

    @NotBlank(message = "Yazar bilgisini giriniz.")
    private String author;

    @NotBlank(message = "Yayın evi bilgisini giriniz.")
    private String publisher;
}
