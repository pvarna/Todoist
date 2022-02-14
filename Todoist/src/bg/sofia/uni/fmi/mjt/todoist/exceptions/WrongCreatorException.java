package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class WrongCreatorException extends RuntimeException {

    public WrongCreatorException() {
    }

    public WrongCreatorException(String message) {
        super(message);
    }

    public WrongCreatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
