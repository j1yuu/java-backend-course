package kkashin.dev.exercise4.repository;

import kkashin.dev.exercise4.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
