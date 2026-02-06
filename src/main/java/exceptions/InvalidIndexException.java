package exceptions;

/**
 * InvalidIndexException is thrown when a task index is out of range.
 * This exception is raised when the user provides a task number that does not
 * correspond to any existing task in the task list.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class InvalidIndexException extends FishballException {
    /**
     * Constructs an InvalidIndexException with the specified error message.
     *
     * @param message the error message describing the exception
     */
    public InvalidIndexException(String message) {
        super(message);
    }
}
