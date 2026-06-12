package kkashin.dev.exceptions;

public class UserAlreadyExistException extends UserException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
