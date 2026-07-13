package kkashin.dev.exercise4.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import kkashin.dev.exercise4.model.Comment;
import kkashin.dev.exercise4.model.dto.CommentDto;
import kkashin.dev.exercise4.model.dto.CreateCommentDto;
import kkashin.dev.exercise4.repository.CommentRepository;
import kkashin.dev.exercise4.repository.PostRepository;
import kkashin.dev.exercise4.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CommentService {
    PostRepository postRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;

    @Transactional
    public CommentDto publishComment(@Valid CreateCommentDto commentDto) {
        var user = userRepository.findById(commentDto.userId()).orElseThrow(
                () -> new EntityNotFoundException("User with given id was not found: %s".formatted(commentDto.userId()))
        );

        var post = postRepository.findById(commentDto.postId()).orElseThrow(
                () -> new EntityNotFoundException("Post with given id was not found: %s".formatted(commentDto.postId()))
        );

        var commentToCreate = Comment.builder()
                .content(commentDto.content())
                .user(user)
                .post(post)
                .build();

        return Comment.toDto(commentRepository.save(commentToCreate));
    }
}
