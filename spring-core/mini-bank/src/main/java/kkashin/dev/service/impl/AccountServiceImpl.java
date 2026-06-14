package kkashin.dev.service.impl;

import kkashin.dev.exceptions.*;
import kkashin.dev.model.Account;
import kkashin.dev.model.User;
import kkashin.dev.model.properties.AccountProperties;
import kkashin.dev.repository.AccountRepository;
import kkashin.dev.repository.UserRepository;
import kkashin.dev.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountProperties accountProperties;

    public AccountServiceImpl (
            UserRepository userRepository,
            AccountRepository accountRepository,
            AccountProperties accountProperties
    ) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountProperties = accountProperties;
    }

    @Override
    public boolean create(int userId) {
        userRepository.getUserById(userId).orElseThrow(
                () -> new UserNotFoundException("User with given ID was not found: " + userId)
        );


        int accountId = accountRepository.create(userId);

        try {
            return userRepository.connectAccountToUser(userId, accountId);
        } catch (UserNotFoundException e) {
            accountRepository.delete(accountId);
            throw e;
        }
    }

    @Override
    public boolean deposit(int accountId, int moneyAmount) {
        if (moneyAmount <= 0) throw new NotEnoughMoneyException("Money should be positive number");

        Account account = accountRepository.getAccountById(accountId);

        int newMoneyAmount = account.getMoneyAmount() + moneyAmount;

        account.setMoneyAmount(newMoneyAmount);
        return true;
    }

    @Override
    public boolean withdraw(int accountId, int moneyAmount) {
        if (moneyAmount <= 0) throw new NotEnoughMoneyException("Money should be positive number");

        Account account = accountRepository.getAccountById(accountId);

        int newMoneyAmount = account.getMoneyAmount() - moneyAmount;
        if (newMoneyAmount < 0)
            throw new NotEnoughMoneyException("Account does not have enough money to complete operation");

        account.setMoneyAmount(newMoneyAmount);
        return true;
    }

    @Override
    public boolean transfer(int accountId, int receiverId, int moneyAmount) {
        if (moneyAmount <= 0) throw new NotEnoughMoneyException("Money should be positive number");

        if (accountId == receiverId)
            throw new AccountException(
                    "Transfer should be proceeded to a different account, currently given accountIds are equal: "
                    + accountId + ", " + receiverId
            );
        Account account = accountRepository.getAccountById(accountId);
        Account receiverAccount = accountRepository.getAccountById(receiverId);

        boolean isSameUser = account.getUserId() == receiverAccount.getUserId();
        int moneyDelta = isSameUser ?
                moneyAmount :
                (int) (moneyAmount * (1 - accountProperties.transferCommission()));

        int newSenderMoneyAmount = account.getMoneyAmount() - moneyAmount;
        int newReceiverMoneyAmount = receiverAccount.getMoneyAmount() + moneyDelta;

        if (newSenderMoneyAmount < 0)
            throw new NotEnoughMoneyException("Account does not have enough money to complete operation");

        account.setMoneyAmount(newSenderMoneyAmount);
        receiverAccount.setMoneyAmount(newReceiverMoneyAmount);

        return true;
    }

    @Override
    public boolean closeAccount(int accountId) {
        Account accountToClose = accountRepository.getAccountById(accountId);
        List<Account> userAccounts = accountRepository.getUserAccounts(accountToClose.getUserId());

        if (userAccounts.size() == 1) throw new OnlyAccountException("Cannot proceed: this is the only user's account.");

        Optional<Account> otherAccount = userAccounts.stream()
                .filter(a -> a.getId() != accountId)
                .findFirst();

        if (otherAccount.isEmpty()) throw new AccountException("Problem occurred in closeAccount");
        Account accountToWithdraw = otherAccount.get();

        int newMoneyAmount = accountToWithdraw.getMoneyAmount() + accountToClose.getMoneyAmount();
        accountToWithdraw.setMoneyAmount(newMoneyAmount);

        Optional<User> expectedUser = userRepository.getUserById(accountToClose.getUserId());
        if (expectedUser.isEmpty()) throw new UserNotFoundException("Owner of this account was not found");

        User user = expectedUser.get();
        user.setAccountList(
                user.getAccountIdList().stream().filter(a -> a != accountId).toList()
        );

        accountRepository.updateAccount(accountToWithdraw);
        return accountRepository.delete(accountId);
    }
}
