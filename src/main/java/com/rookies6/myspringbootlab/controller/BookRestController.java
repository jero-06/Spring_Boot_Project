package com.rookies6.myspringbootlab.controller;

import com.rookies6.myspringbootlab.entity.Book;
import com.rookies6.myspringbootlab.exception.BusinessException;
import com.rookies6.myspringbootlab.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/books")
public class BookRestController {
    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 새 도서 등록
    @PostMapping
    public Book createBook(@RequestBody Book book) {
                return bookRepository.save(book);
    }

    // 모든 도서 조회
    @GetMapping
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    // ID로 특정 도서 조회
    @GetMapping("/{id}")
    public ResponseEntity<Book> getUserById(@PathVariable Long id) {

        return bookRepository.findById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }

    // ISBN으로 도서 조회
    @GetMapping("/isbn/{isbn}")
    public Book getUserByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("책을 찾을 수 없습니다.", HttpStatus.404));
        // Optional이 비어있을 때 그냥 기본값을 리턴하는 게 아니라, "예외를 던지는" 방식의 메서드가 있어요.
        // findById 때 썼던 map/orElse와는 다른, orElse와 이름이 비슷한 메서드입니다. 뭘까요?
    }



}
