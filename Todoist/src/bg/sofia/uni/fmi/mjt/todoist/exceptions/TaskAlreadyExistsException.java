package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class TaskAlreadyExistsException extends RuntimeException {

    public TaskAlreadyExistsException(String message) {
        super(message);
    }

}
