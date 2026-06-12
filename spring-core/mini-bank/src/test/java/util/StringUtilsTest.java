package util;

import kkashin.dev.model.CliCommands;
import kkashin.dev.util.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringUtilsTest {

    @Test
    void stringToCliCommand_shouldReturnValidCommand() {
        String string = "USER_CREATE";

        CliCommands result = StringUtils.stringToCliCommand(string);

        assertEquals(CliCommands.USER_CREATE, result);
    }

    @Test
    void stringToCliCommand_shouldThrowOnNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            StringUtils.stringToCliCommand(null);
        });
    }

    @Test
    void stringToCliCommand_shouldThrowOnInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            StringUtils.stringToCliCommand("input");
        });
    }

    @Test
    void stringTrimOrEmpty_shouldReturnValidTrimmedString() {
        String string = "  234 3   33 ";
        String targetString = "234 3   33";

        String result = StringUtils.stringTrimOrEmpty(string);
        assertEquals(targetString, result);
    }

    @Test
    void stringTrimOrEmpty_shouldReturnEmptyStringWhenNull() {
        String string = null;
        assertEquals("", StringUtils.stringTrimOrEmpty(string));
    }

    @Test
    void convertStringToInt_shouldReturnNumber() {
        String input1 = " 1 ";
        String input2 = "  33";

        int result1 = StringUtils.convertStringToInt(input1);
        int result2 = StringUtils.convertStringToInt(input2);

        assertEquals(1, result1);
        assertEquals(33, result2);
    }

    @Test
    void convertStringToInt_shouldThrowIllegalArgumentException() {
        String input1 = null;
        String input2 = "3w";

        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertStringToInt(input1));
        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertStringToInt(input2));
    }
}
