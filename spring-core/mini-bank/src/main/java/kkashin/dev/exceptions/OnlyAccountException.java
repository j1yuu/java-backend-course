package kkashin.dev.exceptions;

public class OnlyAccountException extends AccountException {
    public OnlyAccountException(String message) {
        super(message);
    }
}
