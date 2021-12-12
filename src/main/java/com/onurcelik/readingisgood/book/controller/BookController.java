package com.onurcelik.readingisgood.book.controller;

import com.onurcelik.readingisgood.book.dto.BookAddInput;
import com.onurcelik.readingisgood.book.dto.BookDetailOutput;
import com.onurcelik.readingisgood.book.dto.BookUpdateInput;
import com.onurcelik.readingisgood.book.service.BookService;
import com.onurcelik.readingisgood.core.dto.ResponseDTO;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Book")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseDTO<List<BookDetailOutput>> getBooks() throws BusinessException {
        return new ResponseDTO<>(HttpStatus.OK, bookService.getBooks());
    }

    @PostMapping
    public ResponseDTO<UUID> addBook(@RequestBody @Valid BookAddInput input) throws BusinessException {
        UUID bookId = bookService.addBook(input);
        return new ResponseDTO<>(HttpStatus.CREATED, bookId);
    }

    @PostMapping("/{bookId}")
    public ResponseDTO<UUID> updateBook(@PathVariable("bookId") UUID bookId, @RequestBody @Valid BookUpdateInput input) throws BusinessException {
        UUID id = bookService.updateBook(bookId, input);
        return new ResponseDTO<>(HttpStatus.OK, id);
    }
}
