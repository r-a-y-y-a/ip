package exceptions;

/**
 * MissingParameterException is thrown when a command is missing required parameters.
 * This exception is raised when the user provides a command without all necessary
 * arguments (e.g., deadline without /by flag, or event without /from or /to flags).
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class MissingParameterException extends FishballException {
    /**
     * Constructs a MissingParameterException with the specified error message.
     *
     * @param message the error message describing the exception
     */
    public MissingParameterException(String message) {
        super(message);
    }
}
