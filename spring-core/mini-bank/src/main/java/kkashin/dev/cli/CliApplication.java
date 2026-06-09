package kkashin.dev.cli;

import kkashin.dev.model.Account;
import kkashin.dev.model.CliCommands;
import kkashin.dev.model.User;
import kkashin.dev.model.dto.UserWithAccountsDto;
import kkashin.dev.service.AccountService;
import kkashin.dev.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class CliApplication {
    private final Scanner scanner;

    private final CliMenu cliMenu;
    private final CliInputReader cliInputReader;

    private final UserService userService;
    private final AccountService accountService;

    public CliApplication(
            UserService userService,
            AccountService accountService
    ) {
        Scanner scanner = new Scanner(System.in);

        this.scanner = scanner;
        this.cliMenu = new CliMenu(scanner);
        this.cliInputReader = new CliInputReader(scanner);

        this.userService = userService;
        this.accountService = accountService;
    }

    public void run() {
        boolean running = true;

        System.out.println("Welcome to the Mini Bank!");
        while (running) {
            cliMenu.show();

            CliCommands option;

            while (true) {
                try {
                    option = cliMenu.readCommand();
                    break;
                } catch (RuntimeException e) {
                    System.out.println("Provided command is not supported. Cause: " + e.getMessage());
                }
            }

            switch (option) {
                case CliCommands.USER_CREATE -> userCreate();
                case CliCommands.SHOW_ALL_USERS -> showAllUsers();
                case CliCommands.ACCOUNT_CREATE -> accountCreate();
                case CliCommands.ACCOUNT_DEPOSIT -> accountDeposit();
                case CliCommands.ACCOUNT_WITHDRAW -> accountWithdraw();
                case CliCommands.ACCOUNT_TRANSFER -> accountTransfer();
                case CliCommands.ACCOUNT_CLOSE -> accountClose();
                case CliCommands.EXIT -> running = false;
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private void accountClose() {
        try {
            int accountId = cliInputReader.readAccountId();
            boolean result = accountService.closeAccount(accountId);

            if (result) {
                System.out.println("Account " + accountId + " was successfully closed");
            } else {
                System.out.println("Account " + accountId + " was not closed");
            }
        } catch (RuntimeException e) {
            System.out.println("Account closing failed: " + e.getMessage());
        }
    }

    private void accountTransfer() {
        try {
            int senderId = cliInputReader.readAccountId();
            int receiverId = cliInputReader.readAccountId();
            int moneyAmount = cliInputReader.readMoneyAmount();
            boolean result = accountService.transfer(senderId, receiverId, moneyAmount);

            if (result)
                System.out.println(
                        "Successfully transfered " + moneyAmount
                                + " money from an account " + senderId
                                + " to an account " + receiverId
                );
        } catch (RuntimeException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    private void accountWithdraw() {
        try {
            int accountId = cliInputReader.readAccountId();
            int moneyAmount = cliInputReader.readMoneyAmount();
            boolean result = accountService.withdraw(accountId, moneyAmount);

            if (result) System.out.println("Successfully withdrew " + moneyAmount + " from an account " + accountId);
        } catch (RuntimeException e) {
            System.out.println("Withdraw failed: " + e.getMessage());
        }
    }

    private void accountDeposit() {
        try {
          int accountId = cliInputReader.readAccountId();
          int moneyAmount = cliInputReader.readMoneyAmount();
          boolean result = accountService.deposit(accountId, moneyAmount);

          if (result) System.out.println("Successfully deposited " + moneyAmount + " money to an account " + accountId);
        } catch (RuntimeException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        };
    }

    private void accountCreate() {
        try {
            int userId = cliInputReader.readUserId();
            boolean result = accountService.create(userId);

            if (result) {
                System.out.println("Account was successfully created for user " + userId);
            }
        } catch(RuntimeException e) {
            System.out.println("Account creation failed: " + e.getMessage());
        }
    }

    private void showAllUsers() {
        List<UserWithAccountsDto> users = userService.getUsers();

        if (users.isEmpty()) {
            System.out.println("No users was found.");
            return;
        }

        System.out.println("Current users: ");
        for (UserWithAccountsDto user : users) {
            System.out.println(user);
        }
    }

    private void userCreate() {
        String login = cliInputReader.readLogin();

        try {
            boolean result = userService.create(login);

            if (result) System.out.println("User was successfully created!");
        } catch (RuntimeException e) {
            System.out.println("User creating failed: " + e.getMessage());
        }
    }
}
