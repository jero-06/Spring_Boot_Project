package com.rookies6.myspringbootlab.repository;

import com.rookies6.myspringbootlab.entity.Book;
import com.rookies6.myspringbootlab.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {

    // 1. 특정 책(bookId)의 상세정보 조회
    Optional<BookDetail> findByBookId(Long bookId);

    // 2. 상세정보와 Book을 함께 조회
    @Query("SELECT b FROM BookDetail b JOIN FETCH b.book WHERE b.id = :id")
    Optional<BookDetail> findByIdWithBook(@Param("id") Long id);

    // 3. 출판사로 상세정보 목록 조회 — 이름 규칙만으로 자동 생성 가능
    List<BookDetail> findByPublisher(String publisher);
}