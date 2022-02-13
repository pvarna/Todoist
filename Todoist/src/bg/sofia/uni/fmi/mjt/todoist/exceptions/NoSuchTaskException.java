package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class NoSuchTaskException extends RuntimeException {

    public NoSuchTaskException() {
    }

    public NoSuchTaskException(String message) {
        super(message);
    }

    public NoSuchTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
