package kkashin.dev.exceptions;

public class NotEnoughMoneyException extends AccountException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
