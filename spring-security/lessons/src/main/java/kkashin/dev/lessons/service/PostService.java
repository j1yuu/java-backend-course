package kkashin.dev.lessons.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kkashin.dev.lessons.model.Post;
import kkashin.dev.lessons.model.PostStatus;
import kkashin.dev.lessons.model.dto.*;
import kkashin.dev.lessons.repository.PostRepository;
import kkashin.dev.lessons.repository.UserRepository;
import kkashin.dev.lessons.util.RequestUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private RequestUtils requestUtils;

    @Transactional
    public PostDto savePost(@Valid CreatePostDto createPostDto) {
        var user = userRepository.findById(createPostDto.userId()).orElseThrow(
                () -> new EntityNotFoundException("User with given id was not found: %s".formatted(createPostDto.userId()))
        );

        var savedPost = postRepository.save(Post
                .builder()
                .title(createPostDto.title())
                .content(createPostDto.content())
                .status(PostStatus.DRAFT)
                .categories(createPostDto.categories() == null ? new HashSet<>() : new HashSet<>(createPostDto.categories()))
                .userEntity(user)
                .build()
        );

        return Post.toDto(savedPost);
    }

    public List<PostDto> findAllPosts(@Valid PostsSearchFilterDto postsSearchFilterDto) {
        var normalizedPageable = requestUtils.normalizePageable(postsSearchFilterDto.pageSize(), postsSearchFilterDto.pageOffset());

        return postRepository.findAll(normalizedPageable).map(Post::toDto).toList();
    }

    public List<PostDto> findUserPosts(@Valid PostsSearchByUserFilterDto postsSearchByUserFilterDto) {
        if (postsSearchByUserFilterDto.userId() == null)
            throw new IllegalArgumentException("User id should be provided");

        userRepository.findById(postsSearchByUserFilterDto.userId()).orElseThrow(
                () -> new EntityNotFoundException("User with given id was not found: %s".formatted(postsSearchByUserFilterDto.userId()))
        );

        var normalizedPageable = requestUtils.normalizePageable(postsSearchByUserFilterDto.pageSize(), postsSearchByUserFilterDto.pageOffset());

        return postRepository.findAllByUserIdPageable(postsSearchByUserFilterDto.userId(), normalizedPageable)
                .stream()
                .map(Post::toDto)
                .toList();
    }

    @Transactional
    public PostDto updatePost(@Valid UpdatePostDto updatePostDto) {
        var post = postRepository.findById(updatePostDto.id()).orElseThrow(
                () -> new EntityNotFoundException("Post with given id was not found: %s".formatted(updatePostDto.id()))
        );

        var postToUpdate = Post.toUpdatedEntity(post, updatePostDto);

        return Post.toDto(postRepository.save(postToUpdate));
    }

    public List<CountByCategoryDto> countPostsByCategories() {
        return postRepository.countPostsByCategories();
    }

    @Transactional
    public PostDto publishPost(@NotNull Long postId) {
        var postToUpdate = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("Post with given id was not found: %s".formatted(postId))
        );

        if (postToUpdate.getStatus().equals(PostStatus.PUBLISHED))
            throw new RuntimeException("Post with given id was already published: %s".formatted(postId));

        postToUpdate.setStatus(PostStatus.PUBLISHED);

        return Post.toDto(postRepository.save(postToUpdate));
    }
}
