package com.rookies6.myspringbootlab.service;

import com.rookies6.myspringbootlab.dto.BookDTO;
import com.rookies6.myspringbootlab.entity.Book;
import com.rookies6.myspringbootlab.entity.BookDetail;
import com.rookies6.myspringbootlab.exception.BusinessException;
import com.rookies6.myspringbootlab.exception.ErrorCode;
import com.rookies6.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
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
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "id", id));
        return BookDTO.Response.fromEntity(book);
    }

    // Isbn 조회
    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "isbn", isbn));
        return BookDTO.Response.fromEntity(book);
    }

    // 작가 조회
    public List<BookDTO.Response> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(book -> BookDTO.Response.fromEntity(book))
                .toList();
    }

    // 제목 조회
    public List<BookDTO.Response> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(book -> BookDTO.Response.fromEntity(book))
                .toList();
    }

    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request) {

        // 1. ISBN 중복 체크 (existsByIsbn 활용) — 중복이면 BusinessException
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        // 2. Request → Book 엔티티 변환 (Request에 toEntity()가 없으니 직접 빌더로)
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        // 3. detailRequest가 있을 때만 BookDetail 생성 + 양방향 연결
        if (request.getDetailRequest() != null) {
            BookDTO.BookDetailDTO detailRequest = request.getDetailRequest();
            BookDetail detail = BookDetail.builder()
                    .description(detailRequest.getDescription())
                    .language(detailRequest.getLanguage())
                    .pageCount(detailRequest.getPageCount())
                    .publisher(detailRequest.getPublisher())
                    .coverImageUrl(detailRequest.getCoverImageUrl())
                    .edition(detailRequest.getEdition())
                    .build();

            book.setBookDetail(detail);
            detail.setBook(book);
        }

        // 4. 저장 후 응답 변환
        Book savedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(savedBook);
    }

    @Transactional
    public BookDTO.Response updateBook(Long id, BookDTO.Request request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "id", id));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        if (request.getDetailRequest() != null) {
            BookDTO.BookDetailDTO detailRequest = request.getDetailRequest();
            BookDetail detail = book.getBookDetail();

            if (detail == null) {
                // 힌트: BookDetail.builder()로 "새 detail"만 만드세요 (Book은 새로 만들지 마세요!)
                detail = BookDetail.builder()
                        .description(detailRequest.getDescription())
                        .language(detailRequest.getLanguage())
                        .pageCount(detailRequest.getPageCount())
                        .publisher(detailRequest.getPublisher())
                        .coverImageUrl(detailRequest.getCoverImageUrl())
                        .edition(detailRequest.getEdition())
                        .build();;
                // detail을 기존 book과 양방향으로 연결
                book.setBookDetail(detail);
                detail.setBook(book);
            }

            // 기존 detail이든 새로 만든 detail이든, 필드 값을 채워넣기
            detail.setDescription(detailRequest.getDescription());
            detail.setLanguage(detailRequest.getLanguage());
            detail.setPageCount(detailRequest.getPageCount());
            detail.setPublisher(detailRequest.getPublisher());
            detail.setCoverImageUrl(detailRequest.getCoverImageUrl());
            detail.setEdition(detailRequest.getEdition());
        }

        Book updatedBook = bookRepository.save(book);
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