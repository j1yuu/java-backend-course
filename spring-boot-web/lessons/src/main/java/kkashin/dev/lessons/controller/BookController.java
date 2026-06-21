package kkashin.dev.lessons.controller;

import jakarta.validation.Valid;
import kkashin.dev.lessons.dto.ServerExceptionDto;
import kkashin.dev.lessons.model.Book;
import kkashin.dev.lessons.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(value = "authorName", required = false) String authorName,
            @RequestParam(value = "maxCost", required = false) Integer maxCost
    ) {
        log.info("Get req for getAllBooks");
        return bookService.searchAllBooks(authorName, maxCost);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        log.info("Get req for getBookById: {}", id);
        return bookService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book bookToCreate) {
        log.info("Post req for bookToCreate: {}", bookToCreate);
        var book = bookService.createBook(bookToCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Delete req for deleteById: {}", id);
        bookService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody @Valid Book book) {
        log.info("Update req for updateBook: {}", book);
        return bookService.updateBook(id, book);
    }
}
