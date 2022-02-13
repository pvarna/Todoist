package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class InvalidNumberOfArgumentsException extends RuntimeException {

    public InvalidNumberOfArgumentsException() {
    }

    public InvalidNumberOfArgumentsException(String message) {
        super(message);
    }

    public InvalidNumberOfArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }
}
