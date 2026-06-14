package kkashin.dev.cli;

import kkashin.dev.util.StringUtils;

import java.util.Scanner;

public class CliInputReader {
    private final Scanner scanner;

    public CliInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readLogin() {
        System.out.print("Enter user login: ");
        return StringUtils.stringTrimOrEmpty(scanner.nextLine());
    }

    public int readUserId() {
        System.out.print("Enter user id: ");
        return StringUtils.convertStringToInt(scanner.nextLine());
    }

    public int readAccountId() {
        System.out.print("Enter account id: ");
        return StringUtils.convertStringToInt(scanner.nextLine());
    }

    public int readMoneyAmount() {
        System.out.print("Enter money amount: ");
        return StringUtils.convertStringToInt(scanner.nextLine());
    }
}
