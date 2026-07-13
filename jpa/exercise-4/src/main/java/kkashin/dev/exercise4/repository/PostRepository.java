package kkashin.dev.exercise4.repository;

import kkashin.dev.exercise4.model.Post;
import kkashin.dev.exercise4.model.PostStatus;
import kkashin.dev.exercise4.model.dto.CountByCategoryDto;
import kkashin.dev.exercise4.model.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findAllByUserIdPageable(Long userId, Pageable pageable);

    @Query("""
    select new kkashin.dev.exercise4.model.dto.CountByCategoryDto(
    category,
    count(p)
    )
    from Post p
    join p.categories category
    group by category
""")
    List<CountByCategoryDto> countPostsByCategories();

    @Modifying
    @Query("""
    update Post p
    set p.status = :status
    where p.id = :id
""")
    int updateStatusById(Long id, PostStatus status);
}
