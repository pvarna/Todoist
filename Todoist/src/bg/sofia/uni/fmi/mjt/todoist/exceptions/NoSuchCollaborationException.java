package bg.sofia.uni.fmi.mjt.todoist.exceptions;

public class NoSuchCollaborationException extends RuntimeException {

    public NoSuchCollaborationException() {
    }

    public NoSuchCollaborationException(String message) {
        super(message);
    }

    public NoSuchCollaborationException(String message, Throwable cause) {
        super(message, cause);
    }
}
