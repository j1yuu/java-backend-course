package kkashin.dev.lessons.repository;

import kkashin.dev.lessons.model.entity.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByAuthorNameIsAndCostLessThan(
            String authorName,
            Integer cost
    );

    @Query("""
        select b from BookEntity b
            where (:authorName is null or b.authorName = :authorName)
                and (:cost is null or b.cost < :cost)
    """)
    List<BookEntity> searchBooks(
            String authorName,
            @Param("cost") Integer maxCost,
            Pageable pageable
    );

    @Query(value = """
        select * from books b
            where (:authorName is null or b.author_name = :authorName)
            and (:cost is null or b.cost < :cost)
    """, nativeQuery = true)
    List<BookEntity> searchBooksNative(
            String authorName,
            @Param("cost") Integer maxCos
    );

    @Modifying
    @Query("""
        update BookEntity b
               set b.name = :name,
                    b.authorName = :authorName,
                    b.publicationYear = :publicationYear,
                    b.pageNumber = :pageNumber,
                    b.cost = :cost
               where b.id = :id
        """)
    void updateBook(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("authorName") String authorName,
            @Param("publicationYear") Integer publicationYear,
            @Param("pageNumber") Integer pageNumber,
            @Param("cost") Integer cost
    );
}
