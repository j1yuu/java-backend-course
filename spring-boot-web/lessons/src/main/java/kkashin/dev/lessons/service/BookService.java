package kkashin.dev.lessons.service;

import kkashin.dev.lessons.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final AtomicLong idCounter;
    private final Map<Long, Book> bookMap;

    public BookService() {
        this.idCounter = new AtomicLong(0);
        this.bookMap = new HashMap<>();

        createBook(new Book(
                null,
                "book 1",
                "author",
                2024,
                100,
                1000
        ));
        createBook(new Book(
                null,
                "book 2",
                "author",
                2025,
                200,
                1000
        ));
        createBook(new Book(
                null,
                "book 3",
                "author 2",
                2022,
                100,
                500
        ));
    }

    public List<Book> searchAllBooks(String authorName, Integer maxCost) {
        return bookMap.values().stream()
                .filter(b -> authorName.isEmpty() || b.authorName().contains(authorName))
                .filter(b -> maxCost == null || b.cost() < maxCost)
                .toList();
    }

    public Book findById(Long id) {
        var book = bookMap.get(id);

        if (book == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        return book;
    }

    public Book createBook(Book bookToCreate) {
        var newId = idCounter.getAndIncrement();

        var newBook = new Book(
                newId,
                bookToCreate.name(),
                bookToCreate.authorName(),
                bookToCreate.publicationYear(),
                bookToCreate.pageNumber(),
                bookToCreate.cost()
        );

        bookMap.put(newId, newBook);

        return newBook;
    }

    public void deleteById(Long id) {
        if (bookMap.get(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");

        bookMap.remove(id);
    }

    public Book updateBook(Long id, Book book) {
        if (bookMap.get(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");

        var updated = new Book(
                id,
                book.name(),
                book.authorName(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost()
        );

        bookMap.put(id, updated);

        return updated;
    }

}
