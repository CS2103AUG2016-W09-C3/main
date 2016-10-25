// @@author A0140155U
package seedu.address.model.task;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;

public class TimeInterval {
    public static final String MESSAGE_TIME_INTERVAL_CONSTRAINTS = "Time interval should be a positive number followed by a character e.g 5h, 1d, 2m, 1w. " +
            "Acceptable time units are m/min/mins (minutes) h/hr/hrs (hours), d/day/days (days), w/week/weeks (weeks)";

    //public static final String INTERVAL_VALIDATION_REGEX = "\\d+\\c";
    private static final Pattern INTERVAL_VALIDATION_REGEX = Pattern.compile("(?<length>\\d+)(?<unit>[a-zA-Z]+)");
    private static HashMap<String, Integer> INTERVAL_TO_MINUTES = new HashMap<>();

    //@@author A0139947L
    private static HashMap<String, String> MINUTE_ALIASES = new HashMap<>();
    //@@author
    
    
    static
    {
        INTERVAL_TO_MINUTES.put("m", 1);
        INTERVAL_TO_MINUTES.put("h", 60);
        INTERVAL_TO_MINUTES.put("d", 24 * 60);
        INTERVAL_TO_MINUTES.put("w", 7 * 24 * 60);
        
        //@@author A0139947L
        MINUTE_ALIASES.put("min", "m");
        MINUTE_ALIASES.put("mins", "m");
        MINUTE_ALIASES.put("hr", "h");
        MINUTE_ALIASES.put("hrs", "h");
        MINUTE_ALIASES.put("day", "d");
        MINUTE_ALIASES.put("days", "d");
        MINUTE_ALIASES.put("week", "w");
        MINUTE_ALIASES.put("weeks", "w");
        //@@author
    }
    
    public final String intervalString;
    public final int length;
    public String unit;
    
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
        
        //@@author A0139947L
        if (MINUTE_ALIASES.containsKey(unit)){
            this.unit = MINUTE_ALIASES.get(unit);
        }
        //@@author
        
        if(!isValidInterval(length, unit)){
            throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
        }
    }
    
    private boolean isValidInterval(int length, String unit){
        if(!INTERVAL_TO_MINUTES.containsKey(unit)){
            return false;
        }
        if(length == 0){
            return false;
        }
        return true;
    }
    
    public int getAsMinutes(){
        return length * INTERVAL_TO_MINUTES.get(unit);
    }
    
    @Override
    public String toString() {
        return length + unit;
    }
    
    public String getUnit(){
    	return unit;
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
//@@author
