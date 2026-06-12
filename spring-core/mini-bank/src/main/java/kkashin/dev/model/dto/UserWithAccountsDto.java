package kkashin.dev.model.dto;

import kkashin.dev.model.Account;

import java.util.List;

public record UserWithAccountsDto(
        int userId,
        String login,
        List<Account> accounts
) {
}
