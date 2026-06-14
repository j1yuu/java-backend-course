package kkashin.dev.cli;

import kkashin.dev.model.CliCommands;
import kkashin.dev.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CliMenu {
    private final Scanner scanner;

    public CliMenu(
            Scanner scanner
    ) {
        this.scanner = scanner;
    }

    public void show() {
        System.out.println("\nChoose an option (1-6):");
        System.out.println(CliCommands.USER_CREATE + ": Create new user");
        System.out.println(CliCommands.SHOW_ALL_USERS + ": Show all users");
        System.out.println(CliCommands.ACCOUNT_CREATE + ": Create an account");
        System.out.println(CliCommands.ACCOUNT_DEPOSIT + ": Deposit money");
        System.out.println(CliCommands.ACCOUNT_WITHDRAW + ": Withdraw money");
        System.out.println(CliCommands.ACCOUNT_TRANSFER + ": Transfer money between accounts");
        System.out.println(CliCommands.ACCOUNT_CLOSE + ": Close an account");
        System.out.println(CliCommands.EXIT + ": Exit\n");
    }

    public CliCommands readCommand() {
        System.out.println("Enter command: ");
        return StringUtils.stringToCliCommand(scanner.nextLine());
    }
}
