package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException(String message) {
        super(message);
    }

}
