package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class CollaborationAlreadyExistsException extends RuntimeException {

    public CollaborationAlreadyExistsException() {
    }

    public CollaborationAlreadyExistsException(String message) {
        super(message);
    }

    public CollaborationAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
