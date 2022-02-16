package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class LogException extends RuntimeException {

    public LogException() {
    }

    public LogException(String message) {
        super(message);
    }

    public LogException(String message, Throwable cause) {
        super(message, cause);
    }
}
