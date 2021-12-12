package com.onurcelik.readingisgood.book.mapper;

import com.onurcelik.readingisgood.book.dto.BookDetailOutput;
import com.onurcelik.readingisgood.book.dto.BookOutput;
import com.onurcelik.readingisgood.book.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {

    private BookMapper() {

    }

    public static List<BookDetailOutput> mapperTo(List<Book> books) {
        List<BookDetailOutput> bookDetailOutputs = new ArrayList<>();
        for(Book book : books) {
            BookDetailOutput output = new BookDetailOutput();
            output.setBookId(book.getId());
            output.setName(book.getName());
            output.setAuthor(book.getAuthor());
            output.setPublisher(book.getPublisher());
            output.setPrice(book.getPrice());
            output.setStock(book.getStock());
            bookDetailOutputs.add(output);
        }
        return bookDetailOutputs;
    }

    public static BookOutput mapperTo(Book book) {
        return BookOutput.builder()
                .name(book.getName())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .build();
    }
}
