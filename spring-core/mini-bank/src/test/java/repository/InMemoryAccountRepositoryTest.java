package repository;

import kkashin.dev.exceptions.AccountNotFoundException;
import kkashin.dev.model.Account;
import kkashin.dev.model.properties.AccountProperties;
import kkashin.dev.repository.AccountRepository;
import kkashin.dev.repository.impl.InMemoryAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryAccountRepositoryTest {
    private AccountProperties accountProperties;
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountProperties = new AccountProperties(
                500,
                0.02
        );
        accountRepository = new InMemoryAccountRepository(
                accountProperties
        );
    }

    @Test
    void create_shouldCreateAccountWhenEmpty() {
        int result = accountRepository.create(1);
        assertEquals(0, result);
    }

    @Test
    void create_shouldReturnIdWhenHasAccounts() {
        accountRepository.create(1);
        int result = accountRepository.create(1);
        assertEquals(1, result);
    }

    @Test
    void create_shouldThrowErrorWhenNegativeUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountRepository.create(-1);
        });
    }

    @Test
    void delete_shouldReturnTrueWhenAccountDeleted() {
        int accountId = accountRepository.create(1);
        boolean result = accountRepository.delete(accountId);

        assertTrue(result);
    }

    @Test
    void delete_shouldReturnFalseWhenAccountNotDeletedOrFound() {
        boolean result = accountRepository.delete(1);

        assertFalse(result);
    }

    @Test
    void getAccountById_shouldReturnProperAccount() {
        int userId = 0;
        int accountId = accountRepository.create(userId);

        Account returnedAccount = accountRepository.getAccountById(accountId);

        assertEquals(accountId, returnedAccount.getId());
        assertEquals(userId, returnedAccount.getUserId());
        assertEquals(accountProperties.defaultAmount(), returnedAccount.getMoneyAmount());
    }

    @Test
    void getAccountById_throwsErrorWhenNotFound() {
        assertThrows(AccountNotFoundException.class, () -> {
            accountRepository.getAccountById(1);
        });
    }

    @Test
    void getUserAccounts_shouldReturnListWhenPresented() {
        int userId = 9;

        Account acc1 = new Account(0, userId, accountProperties.defaultAmount());
        Account acc2 = new Account(1, userId, accountProperties.defaultAmount());
        Account acc3 = new Account(2, userId, accountProperties.defaultAmount());

        accountRepository.create(userId);
        accountRepository.create(userId);
        accountRepository.create(userId);

        List<Account> gotAccounts = accountRepository.getUserAccounts(9);

        assertEquals(3, gotAccounts.size());

        assertTrue(gotAccounts.stream().anyMatch(a -> a.getId() == acc1.getId()));
        assertTrue(gotAccounts.stream().anyMatch(a -> a.getId() == acc2.getId()));
        assertTrue(gotAccounts.stream().anyMatch(a -> a.getId() == acc3.getId()));

        assertTrue(gotAccounts.stream().allMatch(a -> a.getUserId() == userId));
        assertTrue(gotAccounts.stream().
                allMatch((a -> a.getMoneyAmount() == accountProperties.defaultAmount()))
        );

    }

    @Test
    void getUserAccounts_shouldReturnOnlyPresentedUserAccounts() {
        int userId = 9;
        int anotherUserId = 19;

        int acc1id = accountRepository.create(userId);
        int acc2id = accountRepository.create(userId);
        int acc3id = accountRepository.create(anotherUserId);

        List<Account> gotAccounts = accountRepository.getUserAccounts(userId);

        assertEquals(2, gotAccounts.size());

        assertTrue(gotAccounts.stream().anyMatch(a -> a.getId() == acc1id));
        assertTrue(gotAccounts.stream().anyMatch(a -> a.getId() == acc2id));
        assertFalse(gotAccounts.stream().anyMatch(a -> a.getId() == acc3id));

        assertTrue(gotAccounts.stream().allMatch(a -> a.getUserId() == userId));
    }

    @Test
    void getUserAccounts_shouldReturnEmptyListIfEmpty() {
        int userId = 1;
        int accountsSize = accountRepository.getUserAccounts(userId).size();

        assertEquals(0, accountsSize);
    }

    @Test
    void getUserAccounts_shouldReturnEmptyListIfNotFound() {
        int userId = 1;
        int anotherUserId = 2;

        accountRepository.create(anotherUserId);

        int accountsSize = accountRepository.getUserAccounts(userId).size();

        assertEquals(0, accountsSize);
    }

    @Test
    void updateAccount_shouldUpdateMoneyAmountAndReturnUpdatedAccount() {
        int userId = 0;
        int newMoneyAmount = 1000;

        int accountId = accountRepository.create(userId);

        Account accountToUpdate = new Account(
                accountId,
                userId,
                newMoneyAmount
        );

        Account updatedAccount = accountRepository.updateAccount(accountToUpdate);
        Account savedAccount = accountRepository.getAccountById(accountId);

        assertEquals(accountId, updatedAccount.getId());
        assertEquals(userId, updatedAccount.getUserId());
        assertEquals(newMoneyAmount, updatedAccount.getMoneyAmount());

        assertEquals(accountId, savedAccount.getId());
        assertEquals(userId, savedAccount.getUserId());
        assertEquals(newMoneyAmount, savedAccount.getMoneyAmount());
    }

    @Test
    void updateAccount_throwsAccountNotFoundExceptionWhenAccountNotFound() {
        Account accountToUpdate = new Account(
                0,
                1,
                2
        );

        assertThrows(AccountNotFoundException.class, () -> {
            accountRepository.updateAccount(accountToUpdate);
        });
    }

    @Test
    void repository_shouldNotReuseId() {
        int firstId = accountRepository.create(0);
        int secondId = accountRepository.create(9);

        accountRepository.delete(firstId);

        int thirdId = accountRepository.create(2);

        assertNotEquals(secondId, thirdId);
        assertEquals(2, thirdId);
    }
}
