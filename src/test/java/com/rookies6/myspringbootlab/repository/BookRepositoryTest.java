package com.rookies6.myspringbootlab.repository;

import com.rookies6.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
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
                .isbn("0000000000000")
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
        // When(실행단계): isbn으로 조회
        Optional<Book> foundBook = bookRepository.findByIsbn("9788956746432");

        // Then(검증단계)
        assertTrue(foundBook.isPresent());
        assertEquals("JPA 프로그래밍", foundBook.get().getTitle());                    // Optional 안의 Book 객체에서 author를 꺼내서 비교
    }

    // 저자명으로 도서 목록 조회 테스트 ( testFindByAuthor() )
    @Test
    void testFindByAuthor() {
        // When(실행단계): 저자명으로 목록 조회
        List<Book> foundBooks = bookRepository.findByAuthor("홍길동");

        // Then(검증단계)
        assertEquals(2, foundBooks.size());
    }

    // 도서 정보 수정 테스트 ( testUpdateBook() )
    @Test
    void testUpdateBook() {
        // Given(준비단계): 수정할 도서를 isbn으로 조회
        Book book = bookRepository.findByIsbn("9788956746425").get();

        // When(실행단계): 값을 변경하고 저장
        book.setPrice(33333);
        bookRepository.save(book);

        // Then(검증단계): 다시 조회해서 변경된 값 확인
        Book updatedBook = bookRepository.findByIsbn("9788956746425").get();
        assertEquals(33333, updatedBook.getPrice());
    }

    // 도서 삭제 테스트 ( testDeleteBook() )
    @Test
    void testDeleteBook() {

    }
}


