package kkashin.dev.lessons.repository;

import kkashin.dev.lessons.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByAuthorNameIsAndCostLessThan(
            String authorName,
            Integer cost
    );
}
