package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

public class Length {
    
    public static final String MESSAGE_LENGTH_CONSTRAINTS = "Task length should be alphanumeric characters, "
                                                            + "used to denote number of hours.";
    
    public static final String LENGTH_VALIDATION_REGEX = "\\d+";
    
    public final int length;

    public Length(String givenLength) throws IllegalValueException {
        assert givenLength != null;
        givenLength = givenLength.trim();
        
        if (!isValidLength(givenLength)) {
            throw new IllegalValueException(MESSAGE_LENGTH_CONSTRAINTS);
        }
        int numLength = Integer.parseInt(givenLength);
        this.length = numLength;
    }

    private boolean isValidLength(String test) {
        return test.matches(LENGTH_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return this.length + "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Length // instanceof handles nulls
                && this.length == (((Length) other).length)); // state check
    }

    @Override
    public int hashCode() {
        Integer tempLength = (Integer) this.length;
        return tempLength.hashCode();
    }

}
