package kkashin.dev.lessons.service;

import jakarta.persistence.EntityNotFoundException;
import kkashin.dev.lessons.mappers.BookEntityDomainMapper;
import kkashin.dev.lessons.model.Book;
import kkashin.dev.lessons.model.BookSearchFilter;
import kkashin.dev.lessons.model.dto.BookDto;
import kkashin.dev.lessons.model.entity.BookEntity;
import kkashin.dev.lessons.repository.BookRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookEntityDomainMapper bookEntityDomainMapper;
    private final Map<Long, Book> bookMap = new HashMap<>();

    public BookService(
            BookRepository bookRepository,
            BookEntityDomainMapper bookEntityDomainMapper
    ) {
        this.bookRepository = bookRepository;
        this.bookEntityDomainMapper = bookEntityDomainMapper;

        seedBooks();
    }

    public List<Book> searchAllBooks(BookSearchFilter bookSearchFilter) {
        var pageSize = bookSearchFilter.pageSize() != null
                ? bookSearchFilter.pageSize()
                : 3;
        var pageNumber = bookSearchFilter.pageNumber() != null
                ? bookSearchFilter.pageNumber()
                : 0;

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        return bookRepository.searchBooks(bookSearchFilter.authorName(), bookSearchFilter.maxCost(), pageable).stream()
                .map(bookEntityDomainMapper::toDomain)
                .toList();
    }

    public Book findById(Long id) {
        var entity = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book not found: %s".formatted(id))
        );

        return bookEntityDomainMapper.toDomain(entity);
    }

    @Transactional
    public Book createBook(Book bookToCreate) {
        var bookToSave = bookEntityDomainMapper.toEntity(bookToCreate);

        var savedBook = bookRepository.save(bookToSave);

        return bookEntityDomainMapper.toDomain(savedBook);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id))
                throw new EntityNotFoundException("Book not found: %s".formatted(id));

        bookRepository.deleteById(id);
    }

    @Transactional
    public Book updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id))
            throw new EntityNotFoundException("Book not found: %s".formatted(id));

        bookRepository.updateBook(
                id,
                book.name(),
                book.authorName(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost()
        );

        return bookEntityDomainMapper.toDomain(
                bookRepository.findById(id).orElseThrow()
        );
    }

    private void seedBooks() {
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
}
