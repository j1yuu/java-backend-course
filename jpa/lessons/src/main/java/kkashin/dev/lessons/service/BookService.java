package kkashin.dev.lessons.service;

import jakarta.persistence.EntityNotFoundException;
import kkashin.dev.lessons.mappers.BookEntityDomainMapper;
import kkashin.dev.lessons.model.Book;
import kkashin.dev.lessons.model.dto.BookDto;
import kkashin.dev.lessons.model.entity.BookEntity;
import kkashin.dev.lessons.repository.BookRepository;
import org.springframework.stereotype.Service;

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

    public List<Book> searchAllBooks(String authorName, Integer maxCost) {
        return bookRepository.findAllByAuthorNameIsAndCostLessThan(authorName, maxCost).stream()
                .map(bookEntityDomainMapper::toDomain)
                .toList();
    }

    public Book findById(Long id) {
        var entity = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book not found: %s".formatted(id))
        );

        return bookEntityDomainMapper.toDomain(entity);
    }

    public Book createBook(Book bookToCreate) {
        var bookToSave = bookEntityDomainMapper.toEntity(bookToCreate);

        var savedBook = bookRepository.save(bookToSave);

        return bookEntityDomainMapper.toDomain(savedBook);
    }

    public void deleteById(Long id) {
        if (!bookRepository.existsById(id))
                throw new EntityNotFoundException("Book not found: %s".formatted(id));

        bookRepository.deleteById(id);
    }

    public Book updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id))
            throw new EntityNotFoundException("Book not found: %s".formatted(id));

        var entityToUpdate = bookEntityDomainMapper.toEntity(book);
        entityToUpdate.setId(id);

        var updated = bookRepository.save(entityToUpdate);
        return bookEntityDomainMapper.toDomain(updated);
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
