package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class WrongAuthenticationException extends RuntimeException {

    public WrongAuthenticationException() {
    }

    public WrongAuthenticationException(String message) {
        super(message);
    }

    public WrongAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
