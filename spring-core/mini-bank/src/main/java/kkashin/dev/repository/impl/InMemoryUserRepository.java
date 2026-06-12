package kkashin.dev.repository.impl;

import kkashin.dev.exceptions.UserAlreadyExistException;
import kkashin.dev.exceptions.UserNotFoundException;
import kkashin.dev.model.User;
import kkashin.dev.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Set<User> userSet;
    private final AtomicInteger nextId = new AtomicInteger(0);

    public InMemoryUserRepository() {
        this.userSet = new HashSet<>();
    }

    @Override
    public int create(String login, List<Integer> accountIds) {
        String normalizedLogin = validateAndNormalizeLogin(login);

        boolean isUserExists = userSet.stream()
                .anyMatch(u -> u.getLogin().equals(normalizedLogin));

        if (isUserExists)
            throw new UserAlreadyExistException("User with presented login already exists: " + normalizedLogin);

        User newUser = new User(
                nextId.getAndIncrement(),
                normalizedLogin,
                accountIds
        );

        userSet.add(newUser);
        return newUser.getId();
    }

    @Override
    public Optional<User> getUserById(int id) {

        return userSet.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<User> getUserByLogin(String login) {

        return userSet.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public List<User> getUsers() {
        return List.copyOf(userSet);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> expectedUser = userSet.stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst();

        if (expectedUser.isEmpty()) throw new UserNotFoundException("User to update was not found: " + user.toString());

        User userToUpdate = expectedUser.get();
        userToUpdate.setAccountList(user.getAccountIdList());
        userToUpdate.setLogin(user.getLogin());

        return userToUpdate;
    }

    @Override
    public boolean connectAccountToUser(int userId, int accountId) {
        Optional<User> expectedUser = userSet.stream()
                .filter(u -> u.getId() == userId)
                .findFirst();

        if (expectedUser.isEmpty()) throw new UserNotFoundException("User with presented id was not found: " + userId);

        User userToUpdate = expectedUser.get();
        List<Integer> newAccountIds = new ArrayList<>(userToUpdate.getAccountIdList());
        newAccountIds.add(accountId);

        userToUpdate.setAccountList(newAccountIds);
        return true;
    }

    private String validateAndNormalizeLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("User login cannot be null");
        }

        String normalizedLogin = login.strip();

        if (normalizedLogin.length() < 3) {
            throw new IllegalArgumentException(
                    "User login should be at least 3 symbols long"
            );
        }

        return normalizedLogin;
    }
}
