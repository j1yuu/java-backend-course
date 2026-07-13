package kkashin.dev.exercise4.repository;

import jakarta.validation.ValidationException;
import kkashin.dev.exercise4.AbstractIntegrationTest;
import kkashin.dev.exercise4.model.User;
import org.hibernate.NonUniqueObjectException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void save_shouldSaveUser() {
        var user = new User(null, "user1", "email@mail.ru", new ArrayList<>(), Instant.now());
        var savedUser = userRepository.save(user);

        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void save_shouldNotWhenEmailIncorrect() {
        var user = new User(null, "user", null, new ArrayList<>(), Instant.now());
        var user2 = new User(null, "user2", "", new ArrayList<>(), Instant.now());

        assertThrows(ValidationException.class, () -> {
            userRepository.saveAndFlush(user);
        });

        assertThrows(ValidationException.class, () -> {
            userRepository.saveAndFlush(user2);
        });
    }

    @Test
    void save_shouldNotWhenUsernameIncorrect() {
        var user = new User(null, null, "e@mail.ru", new ArrayList<>(), Instant.now());
        var user2 = new User(null, "", "e@mail.su", new ArrayList<>(), Instant.now());

        assertThrows(ValidationException.class, () -> {
            userRepository.saveAndFlush(user);
        });

        assertThrows(ValidationException.class, () -> {
            userRepository.saveAndFlush(user2);
        });
    }

    @Test
    void save_shouldNotWhenUsernameTaken() {
        var user1 = new User(null, "username", "e@mail.ru", new ArrayList<>(), Instant.now());
        var user2 = new User(null, "username", "e@mail.su", new ArrayList<>(), Instant.now());

        userRepository.saveAndFlush(user1);
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(user2);
        });
    }

    @Test
    void save_shouldNotWhenEmailTaken() {
        var user1 = new User(null, "username1", "e@mail.ru", new ArrayList<>(), Instant.now());
        var user2 = new User(null, "username2", "e@mail.ru", new ArrayList<>(), Instant.now());

        userRepository.saveAndFlush(user1);
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(user2);
        });
    }
}
