package repository;

import kkashin.dev.exceptions.UserAlreadyExistException;
import kkashin.dev.exceptions.UserNotFoundException;
import kkashin.dev.model.User;
import kkashin.dev.repository.UserRepository;
import kkashin.dev.repository.impl.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryUserRepositoryTest {
    private UserRepository userRepository;
    private List<Integer> emptyAccountIds;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        emptyAccountIds = new ArrayList<>();
    }

    @Test
    void create_shouldReturnIdWhenCreated() {
        int result = userRepository.create("login", emptyAccountIds);

        assertEquals(0, result);
    }

    @Test
    void create_shouldThrowIllegalArgumentExceptionWhenLoginNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            userRepository.create(null, emptyAccountIds);
        });
    }

    @Test
    void create_shouldThrowIllegalArgumentExceptionWhenLoginLessThen3Length() {
        assertThrows(IllegalArgumentException.class, () -> {
            userRepository.create("23  ", emptyAccountIds);
        });
    }

    @Test
    void create_shouldThrowUserAlreadyExistExceptionWhenLoginTaken() {
        String login = "login";

        userRepository.create(login, emptyAccountIds);

        assertThrows(UserAlreadyExistException.class, () -> {
            userRepository.create(login, emptyAccountIds);
        });
    }

    @Test
    void getUserById_shouldReturnUser() {
        int userId = 0;
        User expectedUser = new User(
                userId,
                "login",
                emptyAccountIds
        );

        userRepository.create("login", emptyAccountIds);

        Optional<User> gotUser = userRepository.getUserById(userId);
        assertTrue(gotUser.isPresent());
        User parsedUser = gotUser.get();

        assertEquals(User.class, parsedUser.getClass());

        assertEquals(expectedUser.getId(), parsedUser.getId());
        assertEquals(expectedUser.getLogin(), parsedUser.getLogin());
        assertEquals(expectedUser.getAccountIdList(), parsedUser.getAccountIdList());
    }

    @Test
    void getUserById_shouldReturnEmptyWhenNotFound() {
        assertEquals(Optional.empty(), userRepository.getUserById(0));
    }

    @Test
    void getUserByLogin_shouldReturnUser() {
        int userId = 0;
        String userLogin = "login";
        User expectedUser = new User(
                userId,
                userLogin,
                emptyAccountIds
        );

        userRepository.create(userLogin, emptyAccountIds);

        Optional<User> gotUser = userRepository.getUserById(userId);

        assertTrue(gotUser.isPresent());

        User parsedUser = gotUser.get();

        assertEquals(User.class, parsedUser.getClass());

        assertEquals(expectedUser.getId(), parsedUser.getId());
        assertEquals(expectedUser.getLogin(), parsedUser.getLogin());
        assertEquals(expectedUser.getAccountIdList(), parsedUser.getAccountIdList());
    }

    @Test
    void getUserByLogin_shouldReturnEmptyWhenNotFound() {
        assertTrue(userRepository.getUserByLogin("login").isEmpty());
    }

    @Test
    void getUsers_shouldReturnValidList() {
        int u1id = 0;
        int u2id = 1;
        int u3id = 2;

        String u1login = "login";
        String u2login = "login1";
        String u3login = "login2";

        userRepository.create(u1login, emptyAccountIds);
        userRepository.create(u2login, emptyAccountIds);
        userRepository.create(u3login, emptyAccountIds);

        List<User> gotUsers = userRepository.getUsers();

        assertEquals(3, gotUsers.size());

        assertTrue(gotUsers.stream().anyMatch(u -> u.getId() == u1id));
        assertTrue(gotUsers.stream().anyMatch(u -> u.getId() == u2id));
        assertTrue(gotUsers.stream().anyMatch(u -> u.getId() == u3id));

        assertTrue(gotUsers.stream().anyMatch(u -> Objects.equals(u.getLogin(), u1login)));
        assertTrue(gotUsers.stream().anyMatch(u -> Objects.equals(u.getLogin(), u2login)));
        assertTrue(gotUsers.stream().anyMatch(u -> Objects.equals(u.getLogin(), u3login)));
    }

    @Test
    void getUsers_shouldReturnEmptyListWhenEmpty() {
        List<User> gotUsers = userRepository.getUsers();

        assertEquals(0, gotUsers.size());
    }

    @Test
    void updateUser_shouldReturnUserWhenSuccess() {
        String userLogin = "login";

        int userId = userRepository.create(userLogin, emptyAccountIds);

        User userToUpdate = new User(
                userId,
                userLogin,
                List.of(1, 2, 3)
        );

        User updatedUser = userRepository.updateUser(userToUpdate);
        Optional<User> unparsedSavedUser = userRepository.getUserById(userId);

        assertTrue(unparsedSavedUser.isPresent());

        User parsedSavedUser = unparsedSavedUser.get();

        assertEquals(userId, updatedUser.getId());
        assertEquals(userLogin, updatedUser.getLogin());
        assertTrue(updatedUser.getAccountIdList().containsAll(userToUpdate.getAccountIdList()));

        assertEquals(userId, parsedSavedUser.getId());
        assertEquals(userLogin, parsedSavedUser.getLogin());
        assertTrue(parsedSavedUser.getAccountIdList().containsAll(userToUpdate.getAccountIdList()));

    }

    @Test
    void updateUser_shouldThrowUserNotFoundExceptionWhenNotFound() {
        User userToUpdate = new User(
                0,
                "login",
                emptyAccountIds
        );

        assertThrows(UserNotFoundException.class, () -> {
            userRepository.updateUser(userToUpdate);
        });
    }

    @Test
    void connectAccountToUser_returnsTrueWhenSuccess() {
        int userId = userRepository.create("login", emptyAccountIds);

        boolean result = userRepository.connectAccountToUser(userId, 1);
        assertTrue(result);
    }

    @Test
    void connectAccountToUser_throwsNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> {
           userRepository.connectAccountToUser(1, 1);
        });
    }
}
