package com.onurcelik.readingisgood.book.service;

import com.onurcelik.readingisgood.book.dto.BookAddInput;
import com.onurcelik.readingisgood.book.dto.BookDetailOutput;
import com.onurcelik.readingisgood.book.dto.BookOutput;
import com.onurcelik.readingisgood.book.dto.BookUpdateInput;
import com.onurcelik.readingisgood.book.entity.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {

    List<BookDetailOutput> getBooks();

    BookOutput getBook(UUID id);

    UUID addBook(BookAddInput input);

    UUID updateBook(UUID id, BookUpdateInput input);

    Book getBookById(UUID id);

    void saveAll(List<Book> books);
}
