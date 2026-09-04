package com.rookies6.myspringbootlab.entity;

import io.micrometer.core.instrument.Meter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;
    private String author;

    @Column(unique = true)
    private String isbn;

    private LocalDate publishDate;
    private Integer price;


}
