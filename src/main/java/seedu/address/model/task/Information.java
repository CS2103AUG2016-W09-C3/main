package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's information in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInformation(String)}
 */
public class Information {
    
    public static final String MESSAGE_INFORMATION_CONSTRAINTS = "Task infomation should be alphanumeric characters, "
            + "used to denote additional information related to task.";

    public static final String INFORMATION_VALIDATION_REGEX = "[\\p{Alnum} ]+";
    
    public final String fullInformation;
    
    /**
     * Validates given information.
     *
     * @throws IllegalValueException if given information string is invalid.
     */
    public Information(String information) throws IllegalValueException {
        assert information != null;
        information = information.trim();
        
        if (!isValidInformation(information)) {
            throw new IllegalValueException(MESSAGE_INFORMATION_CONSTRAINTS);
        }
        this.fullInformation = information;
    }

    /**
     * Returns true if a given string is a valid task information field.
     */
    private boolean isValidInformation(String information) {
        return information.matches(INFORMATION_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return this.fullInformation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Information // instanceof handles nulls
                && this.fullInformation.equals(((Information) other).fullInformation)); // state check
    }

    @Override
    public int hashCode() {
        return this.fullInformation.hashCode();
    }

}
