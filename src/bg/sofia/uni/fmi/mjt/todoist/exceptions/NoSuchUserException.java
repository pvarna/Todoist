package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException() {
    }

    public NoSuchUserException(String message) {
        super(message);
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
