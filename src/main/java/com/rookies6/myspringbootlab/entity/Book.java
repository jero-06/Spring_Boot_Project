package com.rookies6.myspringbootlab.entity;

import io.micrometer.core.instrument.Meter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = Long Id)
    private Long Id;

    private String title;
    private String author;

    @Column(unique = true)
    private String isbn;

    private LocalDate publishDate;
    private Integer price;


}
