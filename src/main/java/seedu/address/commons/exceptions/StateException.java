package seedu.address.commons.exceptions;

/**
 * Signals that a state could not be loaded.
 */
public class StateException extends Exception {
    /**
     * @param message should contain relevant information on why the state failed to load
     */
    public StateException(String message) {
        super(message);
    }
}
