package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException(String message) {
        super(message);
    }
}
