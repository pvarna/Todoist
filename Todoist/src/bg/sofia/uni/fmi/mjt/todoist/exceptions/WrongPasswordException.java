package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }

    public WrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
