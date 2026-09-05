package com.rookies6.myspringbootlab.repository;

import com.rookies6.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    // 도서 등록 테스트 ( testCreateBook() )
    @Test
    void testCreateBook() {
        // Given(준비단계)
        Book book = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025,05,07))
                .build();
        // When(실행단계)
        Book saveBook = bookRepository.save(book);
        // Then(검증단계)
        assertNotNull(saveBook);
        assertEquals("스프링 부트 입문", saveBook.getTitle());

    }

    // ISBN으로 도서 조회 테스트 ( testFindByIsbn() )
    @Test
    void testFindByIsbn() {

    }

}
