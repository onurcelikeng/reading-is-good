package com.onurcelik.readingisgood.book.service.impl;

import com.onurcelik.readingisgood.book.dto.BookAddInput;
import com.onurcelik.readingisgood.book.dto.BookDetailOutput;
import com.onurcelik.readingisgood.book.dto.BookOutput;
import com.onurcelik.readingisgood.book.dto.BookUpdateInput;
import com.onurcelik.readingisgood.book.entity.Book;
import com.onurcelik.readingisgood.book.mapper.BookMapper;
import com.onurcelik.readingisgood.book.repository.BookRepository;
import com.onurcelik.readingisgood.book.service.BookService;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<BookDetailOutput> getBooks() {
        List<Book> books = bookRepository.findAll();
        return BookMapper.mapperTo(books);
    }

    @Override
    public BookOutput getBook(UUID id) {
        return BookMapper.mapperTo(getBookById(id));
    }

    @Override
    public Book getBookById(UUID id) {
        Book book = bookRepository.findBookById(id);
        if(book == null) {
            throw new BusinessException("Book not found!");
        }
        return book;
    }

    @Override
    @Transactional
    public UUID addBook(BookAddInput input) {
        Book book = bookRepository.findBookByNameAndAuthorAndPublisher(input.getName(), input.getAuthor(), input.getPublisher());
        if(book != null) {
            throw new BusinessException("Book is already exist in stocks!");
        }

        Book newBook = bookRepository.save(new Book(input.getName(), input.getAuthor(), input.getPublisher(), input.getPrice(), input.getStock()));
        log.info("Book is created. Id:" + newBook.getId());
        return newBook.getId();
    }

    @Override
    @Transactional
    public UUID updateBook(UUID id, BookUpdateInput input) {
        Book book = bookRepository.findBookById(id);
        if(book == null) {
            throw new BusinessException("Book not found!");
        }

        book.setPrice(input.getPrice());
        book.setStock(input.getStock());
        book.setModifyDate(LocalDateTime.now());
        bookRepository.save(book);
        log.info("Book updated.");

        return book.getId();
    }

    @Override
    public void saveAll(List<Book> books) {
        bookRepository.saveAll(books);
    }
}
