package kkashin.dev.util;

import kkashin.dev.model.CliCommands;

public class StringUtils {
    public static CliCommands stringToCliCommand(String string) {
        if (string == null) throw new IllegalArgumentException("Input should be a valid string");
        try {
            return CliCommands.valueOf(string);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Input should be a value from a list of commands");
        }
    }

    public static String stringTrimOrEmpty(String string) {
        if (string == null) return "";
        return string.trim();
    }

    public static int convertStringToInt(String string) {
        try {
            return Integer.parseInt(string.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("Input should be a valid number");
        }
    }
}
