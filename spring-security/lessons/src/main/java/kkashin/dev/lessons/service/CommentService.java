package kkashin.dev.lessons.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import kkashin.dev.lessons.model.Comment;
import kkashin.dev.lessons.model.dto.CommentDto;
import kkashin.dev.lessons.model.dto.CreateCommentDto;
import kkashin.dev.lessons.repository.CommentRepository;
import kkashin.dev.lessons.repository.PostRepository;
import kkashin.dev.lessons.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .userEntity(user)
                .post(post)
                .build();

        return Comment.toDto(commentRepository.save(commentToCreate));
    }
}
