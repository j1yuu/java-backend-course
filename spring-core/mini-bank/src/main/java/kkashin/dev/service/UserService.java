package kkashin.dev.service;

import kkashin.dev.model.User;
import kkashin.dev.model.dto.UserWithAccountsDto;

import java.util.List;

public interface UserService {
    boolean create(String login);

    List<UserWithAccountsDto> getUsers();

    User getUserById(int id);

    boolean unlinkAccount(int userId, int accountId);
}
