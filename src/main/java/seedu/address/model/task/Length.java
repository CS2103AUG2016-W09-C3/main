package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's length.
 * Functions as a wrapper for TimeInterval
 */
public class Length {

    public final TimeInterval timeInterval;
    public final static String NO_INTERVAL = "";
    public final static String DEFAULT_INTERVAL = "1h";
    public boolean hasInterval = true;
    /**
     * Stores given interval. Validation of interval is done by TimeInterval class.
     *
     * @throws IllegalValueException if given information string is invalid.
     */
    public Length(String interval) throws IllegalValueException {
        assert interval != null;
        interval = interval.trim();
        
        if(interval.equals(NO_INTERVAL)){
            hasInterval = false;
            interval = DEFAULT_INTERVAL;
            
        }
        this.timeInterval = new TimeInterval(interval);
    }

    
    @Override
    public String toString() {
        if(!hasInterval){
            return NO_INTERVAL;
        }else{
            return this.timeInterval.toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrance // instanceof handles nulls
                && this.timeInterval.equals(((Length) other).timeInterval)); // state check
    }

    @Override
    public int hashCode() {
        return this.timeInterval.hashCode();
    }
}
