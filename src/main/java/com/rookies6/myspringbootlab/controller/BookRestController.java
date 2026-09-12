package com.rookies6.myspringbootlab.controller;

import com.rookies6.myspringbootlab.dto.BookDTO;
import com.rookies6.myspringbootlab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    // 모든 도서 조회
    @GetMapping
    public ResponseEntity<List<BookDTO.Response>> getAllBooks() {
        List<BookDTO.Response> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // ID로 특정 도서 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.Response> getBookById(@PathVariable Long id) {
        BookDTO.Response book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // ISBN으로 도서 조회
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.Response> getBookByIsbn(@PathVariable String isbn) {
        BookDTO.Response book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    // 저자로 도서 목록 조회
    @GetMapping("/search/author?author={author}")
    public ResponseEntity<List<BookDTO.Response>> getBooksByAuthor(@RequestParam String author) {
        List<BookDTO.Response> book = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(book);
    }

    // 제목으로 도서 목록 조회
    @GetMapping("/search/author?author={author}")
    public ResponseEntity<List<BookDTO.Response>> getBooksByTitle(@RequestParam String title) {
        List<BookDTO.Response> book = bookService.getBooksByTitle(title);
        return ResponseEntity.ok(book);
    }

    // 새 도서 등록
    @PostMapping
    public ResponseEntity<BookDTO.Response> createBook(
            @Valid @RequestBody BookDTO.Request) {
        BookDTO.Response response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 도서 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.Response> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO.Request) {
        BookDTO.Response response = bookService.updateBook(id, request);
        return ResponseEntity.ok(response);
    }

    // 도서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


}
