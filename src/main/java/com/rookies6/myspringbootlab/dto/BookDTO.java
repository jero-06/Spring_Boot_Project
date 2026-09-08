package com.rookies6.myspringbootlab.dto;

import com.rookies6.myspringbootlab.entity.Book;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class BookDTO {

    // 도서 생성 시 사용되는 DTO
    @Getter
    @Setter
    public static class BookCreateRequest {

        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @NotBlank(message = "저자는 필수입니다.")
        private String author;

        @NotBlank(message = "ISBN은 필수입니다.")
        private String isbn;

        @NotNull(message = "가격은 필수입니다.")
        private Integer price;

        private LocalDate publishDate;

        public Book toEntity() {
            return Book.builder()
                    .title(title)
                    .author(author)
                    .isbn(isbn)
                    .price(price)
                    .publishDate(publishDate)
                    .build();
        }
    }

    // 도서 정보 업데이트 시 사용되는 DTO
    @Getter
    @Setter
    public static class BookUpdateRequest {
        private Integer price;
        private String title;
        private String author;
        private LocalDate publishDate;
    }

    // 클라이언트에게 반환되는 도서 정보 DTO
    @Getter
    @Setter
    @Builder
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public static BookResponse from(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .build();
        }
    }

}