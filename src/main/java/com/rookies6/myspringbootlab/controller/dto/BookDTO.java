package com.rookies6.myspringbootlab.controller.dto;

import com.rookies6.myspringbootlab.entity.Book;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class BookDTO {

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

    @Getter
    @Setter
    public static class BookUpdateRequest {
        private Integer price;
        private String title;
        private String author;
        private LocalDate publishDate;
    }

}