package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time interval recurrence.
 * Functions as a wrapper for TimeInterval
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Recurrance {

    public final TimeInterval timeInterval;
    
    /**
     * Stores given interval. Validation of interval is done by TimeInterval class.
     *
     * @throws IllegalValueException if given information string is invalid.
     */
    public Recurrance(String interval) throws IllegalValueException {
        assert interval != null;
        interval = interval.trim();
        
        this.timeInterval = new TimeInterval(interval);
    }

    
    @Override
    public String toString() {
        return this.timeInterval.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrance // instanceof handles nulls
                && this.timeInterval.equals(((Recurrance) other).timeInterval)); // state check
    }

    @Override
    public int hashCode() {
        return this.timeInterval.hashCode();
    }
}
