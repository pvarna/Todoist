package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class InvalidCommandException extends RuntimeException {

    public InvalidCommandException() {
    }

    public InvalidCommandException(String message) {
        super(message);
    }

    public InvalidCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
