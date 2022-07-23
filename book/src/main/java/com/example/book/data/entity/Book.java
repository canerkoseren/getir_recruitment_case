package com.example.book.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document("book")
public class Book {

    @Id
    private Long id;
    private String title;
    private String description;
    private String writer;
    private String kind;
    private Long stock;
}
