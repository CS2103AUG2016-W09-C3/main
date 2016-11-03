// @@author A0140155U
package todoit.taskbook.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import todoit.taskbook.commons.exceptions.IllegalValueException;
/**
 * Represents a DatedTask's date and time in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLength(String)}
 */
public class DateTime {

    public static final DateTimeFormatter DATE_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
    public static final String PARAM_NOT_SPECIFIED = "-1";

    private final LocalDateTime datetime;
    
    /**
     * Validates given date and time.
     *
     * @throws IllegalValueException if given date or time string is invalid.
     */
    public DateTime(String dateString) throws IllegalValueException {
        this(dateString, false);
    }


    public DateTime(String dateString, boolean fromXML) throws IllegalValueException {
        assert dateString != null;
        dateString = dateString.trim();
        if(fromXML){
            datetime = LocalDateTime.parse(dateString, DATE_DISPLAY_FORMATTER);
        }else{
            datetime = DateParser.parseDate(dateString);
        }
    }

    
    public DateTime(String dateString, DateTime oldDatetime) throws IllegalValueException {
        assert dateString != null;
        assert oldDatetime != null;
        dateString = dateString.trim();
        datetime = DateParser.editDate(dateString, oldDatetime.datetime);
    }
    
    public DateTime (LocalDateTime datetime) {
        this.datetime = datetime;
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
    
    public LocalDateTime getDateTime(){
        return datetime;
    }
}
