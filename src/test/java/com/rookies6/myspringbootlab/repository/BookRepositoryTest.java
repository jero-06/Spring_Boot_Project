package com.rookies6.myspringbootlab.repository;

import com.rookies6.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

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
        // Given(준비단계)
        Book book = Book.builder()
                .title("JPA 프로그래밍")
                .author("박둘리")
                .isbn("9788956746432")
                .price(35000)
                .publishDate(LocalDate.of(2025, 4, 30))
                .build();
        bookRepository.save(book);

        // When(실행단계): isbn으로 조회
        Optional<Book> foundBook = bookRepository.findByIsbn("9788956746432");

        // Then(검증단계)
        assertTrue(foundBook.isPresent());
        assertEquals("JPA 프로그래밍", foundBook.get().getTitle());                    // Optional 안의 Book 객체에서 author를 꺼내서 비교
    }
}


