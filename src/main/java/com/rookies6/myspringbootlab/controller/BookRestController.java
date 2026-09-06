package com.rookies6.myspringbootlab.controller;

import com.rookies6.myspringbootlab.entity.Book;
import com.rookies6.myspringbootlab.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/books")
public class BookRestController {
    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
                return bookRepository.save(book);
    }

    @GetMapping
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }





}
