package service;

import kkashin.dev.exceptions.AccountException;
import kkashin.dev.exceptions.NotEnoughMoneyException;
import kkashin.dev.exceptions.OnlyAccountException;
import kkashin.dev.exceptions.UserNotFoundException;
import kkashin.dev.model.Account;
import kkashin.dev.model.User;
import kkashin.dev.model.properties.AccountProperties;
import kkashin.dev.repository.AccountRepository;
import kkashin.dev.repository.UserRepository;
import kkashin.dev.service.AccountService;
import kkashin.dev.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountProperties accountProperties;

    AccountService accountService;

    @BeforeEach
    void setUp() {
        accountProperties = new AccountProperties(500, 0.02);
        accountService = new AccountServiceImpl(
                userRepository,
                accountRepository,
                accountProperties
        );
    }

    @Test
    void create_shouldReturnTrueIfEverythingCorrect() {
        int userId = 1;
        int accountId = 1;

        Optional<User> returningUser = Optional.of(
                new User(
                        userId,
                        "login",
                        List.of()
                )
        );

        when(accountRepository.create(userId)).thenReturn(accountId);
        when(userRepository.getUserById(userId)).thenReturn(returningUser);
        when(userRepository.connectAccountToUser(userId, accountId)).thenReturn(true);

        assertTrue(accountService.create(userId));
    }

    @Test
    void create_shouldThrowUserNotFoundException() {
        int userId = 1;

        when(userRepository.getUserById(userId)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> {
            accountService.create(userId);
        });

    }

    @Test
    void deposit_shouldReturnTrueWhenSuccess() {
        int accountId = 1;
        int moneyAmount = accountProperties.defaultAmount() - 100;

        Account accountMock = new Account(
                accountId,
                0,
                accountProperties.defaultAmount()
        );

        when(accountRepository.getAccountById(accountId)).thenReturn(accountMock);
        boolean result = accountService.deposit(accountId, moneyAmount);

        assertTrue(result);
        assertEquals(accountProperties.defaultAmount() + moneyAmount, accountMock.getMoneyAmount());
    }

    @Test
    void withdraw_shouldReturnTrueWhenSuccess() {
        int accountId = 1;
        int moneyAmount = accountProperties.defaultAmount() - 100;

        Account accountMock = new Account(
                accountId,
                0,
                accountProperties.defaultAmount()
        );

        when(accountRepository.getAccountById(accountId)).thenReturn(accountMock);
        boolean result = accountService.withdraw(accountId, moneyAmount);

        assertTrue(result);
        assertEquals(accountProperties.defaultAmount() - moneyAmount, accountMock.getMoneyAmount());
    }

    @Test
    void withdraw_shouldThrowNotEnoughMoneyException() {
        int accountId = 1;
        int moneyAmount = accountProperties.defaultAmount() + 200;

        Account accountMock = new Account(
                accountId,
                0,
                accountProperties.defaultAmount()
        );

        when(accountRepository.getAccountById(accountId)).thenReturn(accountMock);

        assertThrows(NotEnoughMoneyException.class, () -> {
           accountService.withdraw(accountId, moneyAmount);
        });
        assertEquals(accountProperties.defaultAmount(), accountMock.getMoneyAmount());
    }

    @Test
    void transfer_shouldProceedWithoutFeeWhenBetweenUserAccounts() {
        int userId = 1;

        int senderId = 1;
        int receiverId = 2;
        int moneyAmount = accountProperties.defaultAmount() - 200;

        Account sender = new Account(
                senderId,
                userId,
                accountProperties.defaultAmount()
        );

        Account receiver = new Account(
                receiverId,
                userId,
                accountProperties.defaultAmount()
        );

        when(accountRepository.getAccountById(senderId)).thenReturn(sender);
        when(accountRepository.getAccountById(receiverId)).thenReturn(receiver);

        assertTrue(accountService.transfer(senderId, receiverId, moneyAmount));
        assertEquals(
                accountProperties.defaultAmount() - moneyAmount,
                sender.getMoneyAmount()
        );
        assertEquals(
                accountProperties.defaultAmount() + moneyAmount,
                receiver.getMoneyAmount()
        );
    }

    @Test
    void transfer_shouldProceedWithFeeWhenBetweenDifferentUsers() {
        int senderId = 1;
        int receiverId = 2;
        int moneyAmount = accountProperties.defaultAmount() - 100;

        Account sender = new Account(
                senderId,
                senderId,
                accountProperties.defaultAmount()
        );

        Account receiver = new Account(
                receiverId,
                receiverId,
                accountProperties.defaultAmount()
        );

        when(accountRepository.getAccountById(senderId)).thenReturn(sender);
        when(accountRepository.getAccountById(receiverId)).thenReturn(receiver);

        assertTrue(accountService.transfer(senderId, receiverId, moneyAmount));
        assertEquals(
                accountProperties.defaultAmount() - moneyAmount,
                sender.getMoneyAmount()
        );
        assertEquals(
                accountProperties.defaultAmount() + (moneyAmount * (1 - accountProperties.transferCommission())),
                receiver.getMoneyAmount()
        );
    }

    @Test
    void transfer_shouldThrowAccountExceptionWhenTransferBetweenSameAccounts() {
        int accountId = 1;
        int moneyAmount = accountProperties.defaultAmount() - 100;

        assertThrows(AccountException.class, () -> {
            accountService.transfer(accountId, accountId, moneyAmount);
        });

        verify(accountRepository, times(0)).getAccountById(accountId);
    }

    @Test
    void transfer_shouldThrowNotEnoughMoneyException() {
        int userId = 1;

        int senderId = 1;
        int receiverId = 2;
        int moneyAmount = accountProperties.defaultAmount() + 100;

        Account sender = new Account(
                senderId,
                userId,
                accountProperties.defaultAmount()
        );

        Account receiver = new Account(
                receiverId,
                userId,
                accountProperties.defaultAmount()
        );

        when(accountRepository.getAccountById(senderId)).thenReturn(sender);
        when(accountRepository.getAccountById(receiverId)).thenReturn(receiver);

        assertThrows(NotEnoughMoneyException.class, () -> {
            accountService.transfer(senderId, receiverId, moneyAmount);
        });

        assertEquals(accountProperties.defaultAmount(), sender.getMoneyAmount());
        assertEquals(accountProperties.defaultAmount(), receiver.getMoneyAmount());
    }

    @Test
    void closeAccount_shouldReturnTrueWhenClosed() {
        int userId = 2;

        int closeId = 1;
        int withdrawId = 2;
        int moneyAmount = 500;

        Account close = new Account(
                closeId,
                userId,
                moneyAmount
        );

        Account withdraw = new Account(
                withdrawId,
                userId,
                moneyAmount
        );

        User user = new User(
                userId,
                "login",
                List.of(closeId, withdrawId)
        );

        List<Account> accountsReturned = Arrays.asList(close, withdraw);

        when(accountRepository.getAccountById(closeId)).thenReturn(close);
        when(accountRepository.getUserAccounts(userId)).thenReturn(accountsReturned);
        when(accountRepository.updateAccount(withdraw)).thenReturn(withdraw);
        when(accountRepository.delete(closeId)).thenReturn(true);
        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));

        assertTrue(accountService.closeAccount(closeId));
    }

    @Test
    void closeAccount_shouldThrowOnlyAccountExpressionWhenOnlyOneAccount() {
        int accountId = 1;
        int userId = 1;

        Account account = new Account(
                accountId,
                userId,
                accountProperties.defaultAmount()
        );
        List<Account> accounts = List.of(account);

        when(accountRepository.getAccountById(accountId)).thenReturn(account);
        when(accountRepository.getUserAccounts(userId)).thenReturn(accounts);

        assertThrows(OnlyAccountException.class, () -> {
            accountService.closeAccount(accountId);
        });
    }
}
