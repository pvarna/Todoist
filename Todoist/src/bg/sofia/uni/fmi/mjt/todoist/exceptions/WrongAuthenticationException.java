package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class WrongAuthenticationException extends RuntimeException {

    public WrongAuthenticationException(String message) {
        super(message);
    }

}
