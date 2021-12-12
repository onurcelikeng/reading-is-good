package com.onurcelik.readingisgood.book.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document("t_books")
public final class Book {

    @Id
    private UUID id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @NotBlank(message = "publisher is mandatory")
    private String publisher;

    @Min(0)
    private double price;

    @Min(0)
    private int stock;


    public Book(String name, String author, String publisher, double price, int stock) {
        LocalDateTime now = LocalDateTime.now();

        this.id = UUID.randomUUID();
        this.createDate = now;
        this.modifyDate = now;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.stock = stock;
    }
}
