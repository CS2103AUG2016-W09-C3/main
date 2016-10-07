package seedu.address.model.task;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;

public class TimeInterval {
    public static final String MESSAGE_TIME_INTERVAL_CONSTRAINTS = "Time interval should be a positive number followed by a character e.g 5h, 1d, 2m, 1w. " +
            "Acceptable time units are h (hour), d (day), w(week)";

    //public static final String INTERVAL_VALIDATION_REGEX = "\\d+\\c";
    private static final Pattern INTERVAL_VALIDATION_REGEX = Pattern.compile("(?<length>\\d+)(?<unit>[a-zA-Z])");
    public static HashMap<String, Integer> INTERVAL_TO_HOURS = new HashMap<>();
    static
    {
        INTERVAL_TO_HOURS = new HashMap<String, Integer>();
        INTERVAL_TO_HOURS.put("h", 1);
        INTERVAL_TO_HOURS.put("d", 24);
        INTERVAL_TO_HOURS.put("w", 7 * 24);
    }
    
    public final String intervalString;
    public final int length;
    public final String unit;
    
    /**
     * Validates given information.
     *
     * @throws IllegalValueException if given information string is invalid.
     */
    public TimeInterval(String intervalString) throws IllegalValueException {
        assert intervalString != null;
        intervalString = intervalString.toLowerCase().trim();

        this.intervalString = intervalString;
        
        final Matcher matcher = INTERVAL_VALIDATION_REGEX.matcher(intervalString);
        if (!matcher.matches()) {
            throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
        }
        length = Integer.parseInt(matcher.group("length"));
        unit = matcher.group("unit");
        if(!isValidInterval(length, unit)){
            throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
        }
    }
    
    private boolean isValidInterval(int length, String unit){
        if(!INTERVAL_TO_HOURS.containsKey(unit)){
            return false;
        }
        if(length == 0){
            return false;
        }
        return true;
    }
    
    public int getAsHours(){
        return length * INTERVAL_TO_HOURS.get(unit);
    }
    
    @Override
    public String toString() {
        return length + unit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeInterval // instanceof handles nulls
                && this.length == ((TimeInterval) other).length
                && this.unit.equals(((TimeInterval) other).unit)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, unit);
    }
}
