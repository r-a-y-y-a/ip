package exceptions;

/**
 * FishballException is the base exception class for all custom exceptions
 * in the Fishball task management application.
 * It extends Exception and provides a common parent for application-specific errors.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class FishballException extends Exception {
    /**
     * Constructs a FishballException with the specified error message.
     *
     * @param message the error message describing the exception
     */
    public FishballException(String message) {
        super(message);
    }
}
