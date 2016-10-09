package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
/**
 * Represents a done status in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLength(String)}
 */
public class DoneFlag {
    
    public static final String DONE = "Done";
    public static final String NOT_DONE = "Not done";

    public static final String MESSAGE_FLAG_CONSTRAINTS = "Done flag should be \"" + DONE + "\" or \"" + NOT_DONE + "\".";
    
    public final String done;
    
    /**
     * Validates given done value.
     *
     * @throws IllegalValueException if given doneFlag string is invalid.
     */

    public DoneFlag(String value) throws IllegalValueException {
        assert value != null;
        value = value.trim();
        if(!isValidFlag(value)){
            
            throw new IllegalValueException(MESSAGE_FLAG_CONSTRAINTS);
        }
        done = value;
    }

    private boolean isValidFlag(String test) {
        return test.equals(DONE) || test.equals(NOT_DONE);
    }
    
    @Override
    public String toString() {
        return this.done;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneFlag // instanceof handles nulls
                && this.done.equals(((DoneFlag) other).done)); // state check
    }

    @Override
    public int hashCode() {
        return done.hashCode();
    }
}
