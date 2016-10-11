package seedu.address.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.commons.exceptions.IllegalValueException;
/**
 * Represents a DatedTask's date and time in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLength(String)}
 */
public class DateTime {
    
    public static final String MESSAGE_INFORMATION_CONSTRAINTS = "Date should be in DDMMYYYY or DDMM format,"
            + " Time should be in HHMM format, in 24h time." +
    " Also, check if your date and time inputs are valid.";

    public static final String DATE_VALIDATION_REGEX = "\\d?[\\d]{7}";
    public static final String TIME_VALIDATION_REGEX = "\\d?[\\d]{4}";
    
    public static final String PARAM_NOT_SPECIFIED = "-1";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
    public static final DateTimeFormatter DATE_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public final LocalDateTime datetime;
    
    /**
     * Validates given date and time.
     *
     * @throws IllegalValueException if given date or time string is invalid.
     */
    public DateTime(String dateString, String timeString) throws IllegalValueException {
        assert timeString != null;
        assert dateString != null;
        timeString = timeString.trim();
        dateString = dateString.trim();
        if (!isValidTime(timeString) || !isValidDate(dateString)) {
            throw new IllegalValueException(MESSAGE_INFORMATION_CONSTRAINTS);
        }
        try{
            datetime = LocalDateTime.parse(dateString + timeString, DATE_FORMATTER);
        }catch(DateTimeParseException ex){
            throw new IllegalValueException(MESSAGE_INFORMATION_CONSTRAINTS);
        }
    }


    public DateTime(String datetimeString) throws IllegalValueException {
        try{
            datetime = LocalDateTime.parse(datetimeString, DATE_FORMATTER);
        }catch(DateTimeParseException ex){
            throw new IllegalValueException(MESSAGE_INFORMATION_CONSTRAINTS);
        }
    }
    
    /**
     * Returns true if a given string is a valid task date field.
     */
    private boolean isValidDate(String date) {
        return date.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid task time field.
     */
    private boolean isValidTime(String time) {
        return time.matches(TIME_VALIDATION_REGEX);
    }

    public String toXMLString() {
        return datetime.format(DATE_FORMATTER);
    }
    
    @Override
    public String toString() {
        return datetime.format(DATE_DISPLAY_FORMATTER);
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.datetime.equals(((DateTime) other).datetime)); // state check
    }

    @Override
    public int hashCode() {
        return this.datetime.hashCode();
    }

}
