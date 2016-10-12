package seedu.address.model.task;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.joestelmach.natty.*;

import seedu.address.commons.exceptions.IllegalValueException;
/**
 * Wrapper class for Natty.
 * Parses dates from a string.
 */
public class DateParser {

    private static Parser nattyParser = new Parser();
    public static LocalDateTime parseDate(String dateString) throws IllegalValueException{
        List<DateGroup> dates = nattyParser.parse(dateString);
        if(dates.isEmpty() || dates.get(0).getDates().isEmpty()){
            throw new IllegalValueException("Date not parsable.");
        }
        return LocalDateTime.ofInstant(dates.get(0).getDates().get(0).toInstant(), ZoneId.systemDefault());
    }
}
