package kkashin.dev.exercise4.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kkashin.dev.exercise4.model.dto.PostDto;
import kkashin.dev.exercise4.model.dto.UpdatePostDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status = PostStatus.DRAFT;

    @ElementCollection
    @CollectionTable(
            name = "post_categories",
            joinColumns = @JoinColumn(name = "post_id")
    )
    @Column(name = "category")
    private Set<String> categories = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public static PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getStatus(),
                new ArrayList<>(post.getCategories()),
                post.getComments().stream().map(Comment::toDto).toList(),
                post.getUser().getId(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public static Post toUpdatedEntity(Post post, UpdatePostDto updatePostDto) {
        return new Post(
                post.getId(),
                updatePostDto.title() == null ? post.getTitle() : updatePostDto.title(),
                updatePostDto.content() == null ? post.getContent() : updatePostDto.content(),
                updatePostDto.status() == null ? post.getStatus() : updatePostDto.status(),
                updatePostDto.categories() == null ? post.getCategories() : new HashSet<>(updatePostDto.categories()),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getUser(),
                post.getComments()
        );
    }
}
