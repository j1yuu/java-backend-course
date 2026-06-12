package kkashin.dev.repository;

import kkashin.dev.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    int create(String login, List<Integer> accountIds);

    Optional<User> getUserById(int id);
    Optional<User> getUserByLogin(String login);

    List<User> getUsers();

    User updateUser(User user);

    boolean connectAccountToUser(int userId, int accountId);
}
