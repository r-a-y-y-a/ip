package exceptions;

public class InvalidCommandException extends FishballException {
    public InvalidCommandException(String message) {
        super(message);
    }
}