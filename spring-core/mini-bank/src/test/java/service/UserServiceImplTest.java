package service;

import kkashin.dev.exceptions.UserNotFoundException;
import kkashin.dev.model.User;
import kkashin.dev.model.dto.UserWithAccountsDto;
import kkashin.dev.repository.AccountRepository;
import kkashin.dev.repository.UserRepository;
import kkashin.dev.service.UserService;
import kkashin.dev.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    AccountRepository accountRepository;

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                userRepository,
                accountRepository
        );
    }

    @Test
    void create_shouldReturnTrueWhenSuccess() {
        int userId = 0;
        String login = "login";
        int accountId = 0;

        when(userRepository.create(login, List.of())).thenReturn(userId);
        when(accountRepository.create(userId)).thenReturn(accountId);
        when(userRepository.connectAccountToUser(userId, accountId)).thenReturn(true);

        assertTrue(userService.create(login));
    }

    @Test
    void getUsers_shouldReturnUsersWhenExist() {
        User user = new User(
                0,
                "login",
                List.of()
        );
        List<User> users = List.of(user);

        when(userRepository.getUsers()).thenReturn(users);

        List<UserWithAccountsDto> result = userService.getUsers();
        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(u -> u.userId() == user.getId()));
        assertTrue(result.stream().anyMatch(u -> Objects.equals(u.login(), user.getLogin())));
    }

    @Test
    void getUsers_shouldReturnEmptyWhenEmpty() {
        List<User> users = List.of();

        when(userRepository.getUsers()).thenReturn(users);

        List<UserWithAccountsDto> result = userService.getUsers();

        assertEquals(0, result.size());
    }

    @Test
    void getUserById_shouldReturnUserWhenExist() {
        int userId = 1;
        User user = new User(
                userId,
                "login",
                List.of()
        );

        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertEquals(user, result);
    }

    @Test
    void getUserById_shouldThrowUserNotFoundExceptionWhenNotFound() {
        int userId = 1;

        when(userRepository.getUserById(userId)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> {
           userService.getUserById(userId);
        });
    }
}
