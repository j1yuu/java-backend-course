package kkashin.dev.service.impl;

import kkashin.dev.exceptions.UserNotFoundException;
import kkashin.dev.model.Account;
import kkashin.dev.model.User;
import kkashin.dev.model.dto.UserWithAccountsDto;
import kkashin.dev.repository.AccountRepository;
import kkashin.dev.repository.UserRepository;
import kkashin.dev.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserServiceImpl (
            UserRepository userRepository,
            AccountRepository accountRepository
    ) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean create(String login) {
        int userId = userRepository.create(login, List.of());

        int accountId = accountRepository.create(userId);
        userRepository.connectAccountToUser(userId, accountId);

        return true;
    }

    @Override
    public List<UserWithAccountsDto> getUsers() {
        List<User> users = userRepository.getUsers();
        List<Account> accounts = accountRepository.getAccounts();

        List<UserWithAccountsDto> result = new ArrayList<>();

        for (User user : users) {
            UserWithAccountsDto userWithAccountsDto = new UserWithAccountsDto(
                    user.getId(),
                    user.getLogin(),
                    accounts.stream().filter(a -> a.getUserId() == user.getId()).toList()
            );

            result.add(userWithAccountsDto);
        }

        return result;
    }

    @Override
    public User getUserById(int id) {
        Optional<User> expectedUser = userRepository.getUserById(id);

        if (expectedUser.isEmpty()) throw new UserNotFoundException("User with presented id does not exist: " + id);

        return expectedUser.get();
    }

    @Override
    public boolean unlinkAccount(int userId, int accountId) {
        Optional<User> expectedUser = userRepository.getUserById(userId);

        if (expectedUser.isEmpty()) throw new UserNotFoundException("User with presented id does not exist: " + userId);

        User userToUpdate = expectedUser.get();
        List<Integer> accountIds = userToUpdate.getAccountIdList();

        userToUpdate.setAccountList(
                accountIds.stream().filter(a -> a != accountId).toList()
        );

        userRepository.updateUser(userToUpdate);
        return true;
    }
}
