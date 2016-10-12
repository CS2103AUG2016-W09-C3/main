package seedu.address.model.task;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.antlr.runtime.tree.Tree;

import com.joestelmach.natty.*;

import seedu.address.commons.exceptions.IllegalValueException;
/**
 * Wrapper class for Natty.
 * Parses dates from a string.
 */
public class DateParser {
    private static final String RELATIVE_DATE_STRING = "RELATIVE_DATE";
    private static final String EXPLICIT_DATE_STRING = "EXPLICIT_DATE";
    private static final String TIME_STRING = "EXPLICIT_TIME";
    
    private static Parser nattyParser = new Parser();
    public static LocalDateTime parseDate(String dateString) throws IllegalValueException{
        return getDateTime(parseNatty(dateString));
    }
    
    public static LocalDateTime editDate(String dateString, LocalDateTime oldDatetime) throws IllegalValueException {
        DateGroup dateGroup = parseNatty(dateString);
        LocalDateTime parsedDate = getDateTime(dateGroup);
        LocalDateTime newDate = oldDatetime;
        if(searchDateTree(dateGroup, RELATIVE_DATE_STRING) || searchDateTree(dateGroup, EXPLICIT_DATE_STRING)){
            newDate = newDate.withYear(parsedDate.getYear()).withDayOfYear(parsedDate.getDayOfYear());
        }
        if(searchDateTree(dateGroup, TIME_STRING)){
            newDate = newDate.withHour(parsedDate.getHour()).withMinute(parsedDate.getMinute());
        }
        return newDate;
    }

    private static boolean searchDateTree(DateGroup dateGroup, String toSearch) {
        Tree textGroups = dateGroup.getSyntaxTree().getChild(0);
        for(int i = 0; i < textGroups.getChildCount(); i++){
            String text = textGroups.getChild(i).getText();
            System.out.println(text);
            if(text.equals(toSearch)){
                return true;
            }
        }
        return false;
    }
    
    private static DateGroup parseNatty(String dateString) throws IllegalValueException{
        List<DateGroup> dates = nattyParser.parse(dateString);
        if(dates.isEmpty() || dates.get(0).getDates().isEmpty()){
            throw new IllegalValueException("Date not parsable.");
        }
        return dates.get(0);
    }
    
    private static LocalDateTime getDateTime(DateGroup dateGroup){
        return LocalDateTime.ofInstant(dateGroup.getDates().get(0).toInstant(), ZoneId.systemDefault());
    }
    
    
}
