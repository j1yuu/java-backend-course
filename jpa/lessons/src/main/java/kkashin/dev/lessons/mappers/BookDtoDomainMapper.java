package kkashin.dev.lessons.mappers;

import kkashin.dev.lessons.model.Book;
import kkashin.dev.lessons.model.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookDtoDomainMapper {
    public BookDto toDto(Book book) {
        return new BookDto(
                book.id(),
                book.name(),
                book.author().id(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost()
        );
    }

    public Book toDomain(BookDto bookDto) {
        return new Book(
                bookDto.id(),
                bookDto.name(),
                null,
                bookDto.publicationYear(),
                bookDto.pageNumber(),
                bookDto.cost()
        );
    }
}
