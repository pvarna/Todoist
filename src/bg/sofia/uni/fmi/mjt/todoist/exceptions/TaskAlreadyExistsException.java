package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class TaskAlreadyExistsException extends RuntimeException {

    public TaskAlreadyExistsException() {
    }

    public TaskAlreadyExistsException(String message) {
        super(message);
    }

    public TaskAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
