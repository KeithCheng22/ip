package keef.exception;

/**
 * Represents exceptions specific to the Keef application.
 *
 * This exception is thrown when an error occurs during the execution
 * of user commands or other application logic.
 */
public class KeefException extends Exception {

    /**
     * Constructs a new KeefException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public KeefException(String message) {
        super(message);
    }
}
