package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class TaskAlreadyCompletedException extends RuntimeException {

    public TaskAlreadyCompletedException() {
    }

    public TaskAlreadyCompletedException(String message) {
        super(message);
    }

    public TaskAlreadyCompletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
