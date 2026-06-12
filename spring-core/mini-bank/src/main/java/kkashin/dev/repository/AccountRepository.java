package kkashin.dev.repository;

import kkashin.dev.model.Account;

import java.util.List;

public interface AccountRepository {
    int create(int userId);

    boolean delete(int id);

    Account getAccountById(int id);

    List<Account> getAccounts();

    List<Account> getUserAccounts(int userId);

    Account updateAccount(Account account);
}
