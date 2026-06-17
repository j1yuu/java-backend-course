package kkashin.dev.lessons.controllers;

import kkashin.dev.lessons.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping
    public Book test() {
        return new Book(
                1L,
                "book-name",
                "author-name",
                2026,
                100,
                1000
        );
    }
}
