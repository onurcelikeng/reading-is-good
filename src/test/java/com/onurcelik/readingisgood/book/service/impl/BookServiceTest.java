package com.onurcelik.readingisgood.book.service.impl;

import com.onurcelik.readingisgood.book.dto.BookAddInput;
import com.onurcelik.readingisgood.book.dto.BookDetailOutput;
import com.onurcelik.readingisgood.book.dto.BookUpdateInput;
import com.onurcelik.readingisgood.book.entity.Book;
import com.onurcelik.readingisgood.book.repository.BookRepository;
import com.onurcelik.readingisgood.book.service.BookService;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository mockBookRepository;

    private BookService bookService;

    private BookAddInput input;
    private BookUpdateInput bookUpdateInput;

    @BeforeEach
    void setup() {
        bookService = new BookServiceImpl(mockBookRepository);

        input = new BookAddInput();
        input.setName("test_name");
        input.setAuthor("test_author");
        input.setPublisher("test_publisher");
        input.setPrice(35);
        input.setStock(25);

        bookUpdateInput = new BookUpdateInput();
        bookUpdateInput.setPrice(100);
        bookUpdateInput.setStock(2);
    }

    @Test
    void testGetBooks_valid_returnList() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("test_name_1", "test_author_1", "test_publisher_1", 25, 10));
        books.add(new Book("test_name_2", "test_author_2", "test_publisher_2", 30, 2));

        when(mockBookRepository.findAll()).thenReturn(books);

        List<BookDetailOutput> bookDetailOutputs = bookService.getBooks();
        Assertions.assertEquals(2, bookDetailOutputs.size());
    }

    @Test
    void testGetBookById_valid_returnBook() {
        Book book = new Book("test_name_1", "test_author_1", "test_publisher_1", 25, 10);
        UUID bookId = book.getId();

        when(mockBookRepository.findBookById(book.getId())).thenReturn(book);

        Book response = bookService.getBookById(bookId);
        Assertions.assertEquals(book.getId(), response.getId());
    }

    @Test
    void testGetBookById_bookNotExist_throwsException() {
        UUID bookId = UUID.randomUUID();

        when(mockBookRepository.findBookById(bookId)).thenReturn(null);
        Assertions.assertThrows(BusinessException.class, () -> bookService.getBook(bookId));
    }

    @Test
    void testAddBook_bookNotExist_returnUUID() {
        Book book = new Book(input.getName(), input.getAuthor(), input.getPublisher(), input.getPrice(), input.getStock());

        when(mockBookRepository.findBookByNameAndAuthorAndPublisher(input.getName(), input.getAuthor(), input.getPublisher())).thenReturn(null);
        when(mockBookRepository.save(any())).thenReturn(book);

        UUID response = bookService.addBook(input);
        Assertions.assertEquals(book.getId(), response);
    }

    @Test
    void testAddBook_bookExist_throwsException() {
        Book book = new Book(input.getName(), input.getAuthor(), input.getPublisher(), input.getPrice(), input.getStock());

        when(mockBookRepository.findBookByNameAndAuthorAndPublisher(input.getName(), input.getAuthor(), input.getPublisher())).thenReturn(book);
        Assertions.assertThrows(BusinessException.class, () -> bookService.addBook(input));
    }

    @Test
    void testUpdateBook_valid_returnUUID() {
        Book book = new Book(input.getName(), input.getAuthor(), input.getPublisher(), input.getPrice(), input.getStock());

        when(mockBookRepository.findBookById(book.getId())).thenReturn(book);
        when(mockBookRepository.save(any())).thenReturn(book);

        UUID response = bookService.updateBook(book.getId(), bookUpdateInput);
        Assertions.assertEquals(book.getId(), response);
    }

    @Test
    void testUpdateBook_bookNotFound_throwsException() {
        UUID bookId = UUID.randomUUID();

        when(mockBookRepository.findBookById(bookId)).thenReturn(null);
        Assertions.assertThrows(BusinessException.class, () -> bookService.updateBook(bookId, bookUpdateInput));
    }
}