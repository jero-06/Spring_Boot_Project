package com.rookies6.myspringbootlab.controller;

import com.rookies6.myspringbootlab.entity.Book;
import com.rookies6.myspringbootlab.repository.BookRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/books")
public class BookRestController {
    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        // 요청 본문(JSON)을 Book 객체로 받으려면 파라미터 앞에 어떤 애노테이션이 필요할까요?
        return bookRepository.save(book);   // book을 DB에 저장하고, 그 저장된 결과를 리턴하려면?
    }





}
