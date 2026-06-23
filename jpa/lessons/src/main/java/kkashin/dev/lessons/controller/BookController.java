package kkashin.dev.lessons.controller;

import jakarta.validation.Valid;
import kkashin.dev.lessons.mappers.BookDtoDomainMapper;
import kkashin.dev.lessons.model.dto.BookDto;
import kkashin.dev.lessons.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final BookDtoDomainMapper bookDtoDomainMapper;

    public BookController(BookService bookService, BookDtoDomainMapper bookDtoDomainMapper) {
        this.bookService = bookService;
        this.bookDtoDomainMapper = bookDtoDomainMapper;
    }

    @GetMapping
    public List<BookDto> getAllBooks(
            @RequestParam(value = "authorName", required = false) String authorName,
            @RequestParam(value = "maxCost", required = false) Integer maxCost
    ) {
        log.info("Get req for getAllBooks");

        var books = bookService.searchAllBooks(authorName, maxCost);

        return books.stream().map(bookDtoDomainMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        log.info("Get req for getBookById: {}", id);

        var book = bookService.findById(id);

        return bookDtoDomainMapper.toDto(book);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDtoToCreate) {
        log.info("Post req for bookToCreate: {}", bookDtoToCreate);

        var domain = bookDtoDomainMapper.toDomain(bookDtoToCreate);

        var book = bookService.createBook(domain);
        var dto = bookDtoDomainMapper.toDto(book);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Delete req for deleteById: {}", id);
        bookService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody @Valid BookDto bookDto) {
        log.info("Update req for updateBook: {}", bookDto);

        var domain = bookDtoDomainMapper.toDomain(bookDto);

        var book = bookService.updateBook(id, domain);

        return bookDtoDomainMapper.toDto(book);
    }
}
