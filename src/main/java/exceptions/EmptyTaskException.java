package exceptions;

/**
 * EmptyTaskException is thrown when a task with an empty description is created.
 * This exception is raised when a user attempts to add a todo, deadline, or event
 * without providing a valid task description.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public final class EmptyTaskException extends FishballException {
    /**
     * Constructs an EmptyTaskException with the specified error message.
     *
     * @param message the error message describing the exception
     */
    public EmptyTaskException(String message) {
        super(message);
    }
}
