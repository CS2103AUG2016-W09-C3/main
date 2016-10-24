// @@author A0140155U
package seedu.address.model.task;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateParserTest {

    public static final DateTimeFormatter DATE_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    
    @Test
    public void checkSimpleDates() {
        LocalDateTime datetime = parseDate("01-01-2015 15:00");
        try {
            assertEquals(datetime, DateParser.parseDate("01-01-2015 15:00"));
            assertEquals(datetime, DateParser.parseDate("01/01/2015 1500"));
            assertEquals(datetime, DateParser.parseDate("15:00 01-01-2015"));
            assertEquals(datetime, DateParser.parseDate("01-01-2015 3pm"));
            assertEquals(datetime, DateParser.parseDate("3pm 01-01-2015"));
            assertEquals(datetime, DateParser.parseDate("1st Jan 2015 3pm"));
            assertEquals(datetime, DateParser.parseDate("1st January 2015 3pm"));
        } catch (IllegalValueException e) {
            fail("Cannot parse date");
        }
    }
    
    @Test
    public void editTime() {
        LocalDateTime datetime = parseDate("01-01-2015 15:00");
        LocalDateTime newDatetime = parseDate("01-01-2015 17:00");
        try {
            assertEquals(newDatetime, DateParser.editDate("5 pm", datetime));
            assertEquals(newDatetime, DateParser.editDate("17:00", datetime));
        } catch (IllegalValueException e) {
            fail("Cannot parse date");
        }
    }
    
    @Test
    public void editDate() {
        LocalDateTime datetime = parseDate("01-01-2015 15:00");
        LocalDateTime newDatetime = parseDate("03-03-2015 15:00");
        try {
            assertEquals(newDatetime, DateParser.editDate("03-03-2015", datetime));
            assertEquals(newDatetime, DateParser.editDate("3rd Mar 2015", datetime));
        } catch (IllegalValueException e) {
            fail("Cannot parse date");
        }
    }
    
    @Test
    public void editDateTime() {
        LocalDateTime datetime = parseDate("01-01-2015 15:00");
        LocalDateTime newDatetime = parseDate("03-03-2015 17:00");
        try {
            assertEquals(newDatetime, DateParser.editDate("03-03-2015 17:00", datetime));
            assertEquals(newDatetime, DateParser.editDate("3rd Mar 2015 5pm", datetime));
        } catch (IllegalValueException e) {
            fail("Cannot parse date");
        }
    }
    
    private LocalDateTime parseDate(String date){
        return LocalDateTime.parse(date, DATE_DISPLAY_FORMATTER);
    }
}
//@@author