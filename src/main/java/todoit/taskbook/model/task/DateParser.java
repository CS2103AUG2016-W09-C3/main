// @@author A0140155U
package todoit.taskbook.model.task;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.antlr.runtime.tree.Tree;

import com.joestelmach.natty.*;

import todoit.taskbook.commons.exceptions.IllegalValueException;
/**
 * Wrapper class for Natty.
 * Parses dates from a string.
 */
public class DateParser {
    private static final String RELATIVE_DATE_STRING = "RELATIVE_DATE";
    private static final String EXPLICIT_DATE_STRING = "EXPLICIT_DATE";
    private static final String TIME_STRING = "EXPLICIT_TIME";
    
    private static Parser nattyParser = new Parser();
    
    /*
     * Parses a date time from a string using natty.
     */
    public static LocalDateTime parseDate(String dateString) throws IllegalValueException{
        DateGroup dateGroup = parseNatty(dateString);
        LocalDateTime parsedDate = getDateTime(dateGroup);
        // Default to midnight if time is not specifed.
        if(!isTimeSpecified(dateGroup)){
            parsedDate = parsedDate.withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        return parsedDate;
    }
    
    
    /*
     * Edits a date based on a string parsed using natty.
     * Only edits the old date or time if a new date or time is provided respectively.
     * E.g.
     * dateString: 5pm => Only Time changes
     * dateString: tomorrow => Only Date changes
     * dateString: 5pm tomorrow => Both change
     */
    public static LocalDateTime editDate(String dateString, LocalDateTime oldDatetime) throws IllegalValueException {
        DateGroup dateGroup = parseNatty(dateString);
        LocalDateTime parsedDate = getDateTime(dateGroup);
        LocalDateTime newDate = oldDatetime;
        if(isDateSpecified(dateGroup)){
            newDate = newDate.withYear(parsedDate.getYear()).withDayOfYear(parsedDate.getDayOfYear());
        }
        if(isTimeSpecified(dateGroup)){
            newDate = newDate.withHour(parsedDate.getHour()).withMinute(parsedDate.getMinute());
        }
        return newDate;
    }
    
    //@@author A0139046E
    /*
     * Reschedule a date based on a string rescheduleInterval parsed using TimeInterval 
     * Time interval added by to the current datetime using natty
     *
     */
    public static LocalDateTime rescheduleDate(LocalDateTime rescheduleDatetime, String rescheduleInterval) throws IllegalValueException{
    	TimeInterval timeInterval = new TimeInterval(rescheduleInterval);
        int minutes = timeInterval.getAsMinutes();
        rescheduleDatetime = rescheduleDatetime.plusMinutes(minutes);

    	return rescheduleDatetime;
    }
    //@@author
    // @@author A0140155U

    
    /*
     * Checks a parsed date for a time field (e.g. 5pm, 1700h, noon etc)
     */
    private static boolean isTimeSpecified(DateGroup dateGroup) {
        return searchDateTree(dateGroup, TIME_STRING);
    }

    /*
     * Checks a parsed date for a date field (e.g. Nov 5th, yesterday, 11-05-2016 etc)
     */
    private static boolean isDateSpecified(DateGroup dateGroup) {
        return searchDateTree(dateGroup, RELATIVE_DATE_STRING) || searchDateTree(dateGroup, EXPLICIT_DATE_STRING);
    }
    
    /*
     * Searches natty's generated date tree for a string value
     * Used to check if the date string has a date or time by searching the date tree generated by natty.
     * Refer to natty documentation for the tree layout.
     */
    private static boolean searchDateTree(DateGroup dateGroup, String toSearch) {
        Tree textGroups = dateGroup.getSyntaxTree().getChild(0);
        for(int i = 0; i < textGroups.getChildCount(); i++){
            String text = textGroups.getChild(i).getText();
            if(text.equals(toSearch)){
                return true;
            }
        }
        return false;
    }
    
    /*
     * Parses a date using natty.
     */
    private static DateGroup parseNatty(String dateString) throws IllegalValueException{
        if(dateString == null){
            throw new IllegalValueException("Missing date parameter.");
        }
        List<DateGroup> dates = nattyParser.parse(dateString);
        if(dates.isEmpty() || dates.get(0).getDates().isEmpty()){
            throw new IllegalValueException("Date not parsable.");
        }
        return dates.get(0);
    }
    
    /*
     * Converts a natty DateGroup into a LocalDateTime.
     */
    private static LocalDateTime getDateTime(DateGroup dateGroup){
        return LocalDateTime.ofInstant(dateGroup.getDates().get(0).toInstant(), ZoneId.systemDefault());
    }
    
}
//@@author
