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
                .orElseThrow(() -> new BusinessException("해당 번호의 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // 도서 정보 수정
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 Id의 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPrice(bookDetails.getPrice());
        book.setPublishDate(bookDetails.getPublishDate());

        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 Id의 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        bookRepository.delete(book);

        return ResponseEntity.noContent().build();
    }


}
