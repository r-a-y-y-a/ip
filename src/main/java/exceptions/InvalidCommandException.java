package exceptions;

/**
 * InvalidCommandException is thrown when the user enters an unrecognized or invalid command.
 * This exception is raised when the command parser encounters a command that is not
 * supported by the Fishball application.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class InvalidCommandException extends FishballException {
    /**
     * Constructs an InvalidCommandException with the specified error message.
     *
     * @param message the error message describing the exception
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
