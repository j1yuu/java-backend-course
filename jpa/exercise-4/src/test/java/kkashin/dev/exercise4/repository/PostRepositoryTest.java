package kkashin.dev.exercise4.repository;

import kkashin.dev.exercise4.AbstractIntegrationTest;
import kkashin.dev.exercise4.model.Post;
import kkashin.dev.exercise4.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        var users = userRepository.saveAllAndFlush(
                List.of(
                        createUser("username1", "email@mail.ru"),
                        createUser("username2", "email@mail.su")
                )
        );

        user1 = users.get(0);
        user2 = users.get(1);
    }

    @Test
    void save_shouldSavePost() {
        var post = createPost("some title", "some content", user1);
        var createdPost = postRepository.save(post);

        assertEquals(post.getTitle(), createdPost.getTitle());
        assertEquals(post.getContent(), createdPost.getContent());
    }

    private User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }

    private Post createPost(String title, String content, User user) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);

        return post;
    }
}
