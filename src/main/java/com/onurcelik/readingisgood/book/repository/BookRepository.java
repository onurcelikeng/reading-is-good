package com.onurcelik.readingisgood.book.repository;

import com.onurcelik.readingisgood.book.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BookRepository extends MongoRepository<Book, UUID> {

    Book findBookById(UUID id);

    Book findBookByNameAndAuthorAndPublisher(String name, String author, String publisher);
}
