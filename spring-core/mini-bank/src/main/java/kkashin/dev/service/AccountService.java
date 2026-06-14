package kkashin.dev.service;

import kkashin.dev.model.Account;

import java.util.List;

public interface AccountService {
    boolean create(int userId);
    boolean deposit(int accountId, int moneyAmount);
    boolean withdraw(int accountId, int moneyAmount);
    boolean transfer(int accountId, int receiverId, int moneyAmount);
    boolean closeAccount(int accountId);
}
