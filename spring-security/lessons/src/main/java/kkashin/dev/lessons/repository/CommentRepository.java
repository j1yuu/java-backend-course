package kkashin.dev.lessons.repository;

import kkashin.dev.lessons.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
