package com.rookies6.myspringbootlab.service;

import com.rookies6.myspringbootlab.dto.BookDTO;
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

    public List<BookDTO.BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> BookDTO.BookResponse.from(book))                    // Book 하나를 BookResponse로 변환하려면, 우리가 아까 만든 어떤 메서드를 쓰면 될까요?
                .toList();
    }

}