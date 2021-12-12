package com.onurcelik.readingisgood.book.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookOutput implements Serializable {

    private String name;
    private String author;
    private String publisher;
}
