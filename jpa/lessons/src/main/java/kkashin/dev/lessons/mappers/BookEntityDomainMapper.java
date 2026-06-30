package kkashin.dev.lessons.mappers;

import kkashin.dev.lessons.model.Book;
import kkashin.dev.lessons.model.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookEntityDomainMapper {
    public BookEntity toEntity(Book book) {
        return new BookEntity(
                book.id(),
                book.name(),
                book.author(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost()
        );
    }

    public Book toDomain(BookEntity bookEntity) {
        return new Book(
                bookEntity.getId(),
                bookEntity.getName(),
                bookEntity.getAuthor(),
                bookEntity.getPublicationYear(),
                bookEntity.getPageNumber(),
                bookEntity.getCost()
        );
    }
}
