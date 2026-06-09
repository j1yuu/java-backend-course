package kkashin.dev.repository.impl;

import kkashin.dev.exceptions.AccountNotFoundException;
import kkashin.dev.model.Account;
import kkashin.dev.model.properties.AccountProperties;
import kkashin.dev.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryAccountRepository implements AccountRepository {
    private final Set<Account> accountSet;
    private final AccountProperties accountProperties;
    private final AtomicInteger nextId = new AtomicInteger(0);

    public InMemoryAccountRepository(
            AccountProperties accountProperties
    ) {
        this.accountProperties = accountProperties;
        this.accountSet = new HashSet<>();
    }

    @Override
    public int create(int userId) {
        if (userId < 0) throw new IllegalArgumentException("userId cannot be negative");

        Account account = new Account(
                nextId.getAndIncrement(),
                userId,
                accountProperties.defaultAmount()
        );

        accountSet.add(account);
        return account.getId();
    }

    @Override
    public boolean delete(int id) {
        return accountSet.removeIf((a) -> a.getId() == id);
    }

    @Override
    public Account getAccountById(int id) {
        Optional<Account> account = accountSet.stream()
                .filter(a -> a.getId() == id)
                .findFirst();

        if (account.isPresent()) return account.get();

        throw new AccountNotFoundException("No existing account with an id of" + id);
    }

    @Override
    public List<Account> getAccounts() {
        return List.copyOf(accountSet);
    }

    @Override
    public List<Account> getUserAccounts(int userId) {

        return List.copyOf(
                accountSet.stream()
                .filter(a -> a.getUserId() == userId)
                .toList());
    }

    @Override
    public Account updateAccount(Account account) {
        Optional<Account> savedAccount = accountSet.stream()
                .filter(a -> a.getId() == account.getId())
                .findFirst();

        if (savedAccount.isEmpty()) throw new AccountNotFoundException("Account to update was not found");

        Account parsedAccount = savedAccount.get();
        parsedAccount.setMoneyAmount(account.getMoneyAmount());

        return parsedAccount;
    }
}
