package com.rookies6.myspringbootlab.service;

import com.rookies6.myspringbootlab.dto.BookDTO;
import com.rookies6.myspringbootlab.entity.Book;
import com.rookies6.myspringbootlab.exception.BusinessException;
import com.rookies6.myspringbootlab.exception.ErrorCode;
import com.rookies6.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 모든 도서 목록 조회
    public List<BookDTO.Response> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> BookDTO.Response.fromEntity(book))
                .toList();
    }

    // Id 조회
    public BookDTO.Response getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 Id의 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    // Isbn 조회
    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("해당 번호의 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    // 작가 조회
    public List<BookDTO.Response> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(book -> BookDTO.Response.fromEntity(book))
                .toList();
    }

    @Transactional
    public BookDTO.Response createBook(BookDTO.BookCreateRequest request) {
        Book book = request.toEntity();
        Book savedBook = bookRepository.save(book);
        return BookDTO.Response.from(savedBook);
    }

    @Transactional
    public BookDTO.Response updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "id", id));

        if (request.getTitle() != null) {
            existBook.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            existBook.setAuthor(request.getAuthor());
        }
        if (request.getPrice() != null) {
            existBook.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            existBook.setPublishDate(request.getPublishDate());
        }

        Book updatedBook = bookRepository.save(existBook);
        return BookDTO.Response.fromEntity(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Book", "id", id);
        }
        bookRepository.deleteById(id);

    }
}