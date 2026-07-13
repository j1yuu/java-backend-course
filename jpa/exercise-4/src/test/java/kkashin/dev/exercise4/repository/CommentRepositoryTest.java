package kkashin.dev.exercise4.repository;

import kkashin.dev.exercise4.AbstractIntegrationTest;
import kkashin.dev.exercise4.model.Comment;
import kkashin.dev.exercise4.model.Post;
import kkashin.dev.exercise4.model.PostStatus;
import kkashin.dev.exercise4.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    User user;
    Post post;

    @BeforeEach
    void setUp() {
        user = userRepository.saveAndFlush(new User(null, "user", "e@mail.ru", new ArrayList<>(), Instant.now()));
        post = postRepository.saveAndFlush(createPost("title", "content", user));
    }

    @Test
    void save_shouldSave() {
        var comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent("some comment");

        var savedComment = commentRepository.saveAndFlush(comment);
        assertEquals(comment.getContent(), savedComment.getContent());
    }

    private Post createPost(String title, String content, User user) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        post.setStatus(PostStatus.PUBLISHED);
        post.setCategories(new HashSet<>());

        return post;
    }
}
