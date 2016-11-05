// @@author A0140155U
package todoit.taskbook.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.model.task.TimeInterval;

public class TimeIntervalTest {

    @Test
    public void timeInterval_validIntervals_parsedSuccessfully() {
        try {
            TimeInterval t;
            t = new TimeInterval("10m");
            assertEquals(t.getAsMinutes(), 10);
            t = new TimeInterval("4h");
            assertEquals(t.getAsMinutes(), 4 * 60);
            t = new TimeInterval("40h");
            assertEquals(t.getAsMinutes(), 40 * 60);
            t = new TimeInterval("2d");
            assertEquals(t.getAsMinutes(), 2 * 24 * 60);
            t = new TimeInterval("3w");
            assertEquals(t.getAsMinutes(), 3 * 7 * 24 * 60);
            // @@author
            
            //@@author A0139947L
            // Check if TimeInterval is able to get different aliases
            t = new TimeInterval("6min");
            assertEquals(t.getAsMinutes(), 6);
            t = new TimeInterval("9mins");
            assertEquals(t.getAsMinutes(), 9);
            t = new TimeInterval("18hr");
            assertEquals(t.getAsMinutes(), 18 * 60);
            t = new TimeInterval("7hrs");
            assertEquals(t.getAsMinutes(), 7 * 60);
            t = new TimeInterval("55day");
            assertEquals(t.getAsMinutes(), 55 * 24 * 60);
            t = new TimeInterval("100days");
            assertEquals(t.getAsMinutes(), 100 * 24 * 60);
            t = new TimeInterval("2week");
            assertEquals(t.getAsMinutes(), 2 * 7 * 24 * 60);
            t = new TimeInterval("9weeks");
            assertEquals(t.getAsMinutes(), 9 * 7 * 24 * 60);
            //@@author
            
            // @@author A0140155U
        } catch (IllegalValueException e) {
            fail("Could not parse time interval");
        }
    }
    
    @Test
    public void timeInterval_invalidIntervals_failToParse() {
        checkInvalidInterval("1");
        checkInvalidInterval("h");
        checkInvalidInterval("1 h");
        checkInvalidInterval("0.5h");
        checkInvalidInterval("");
        checkInvalidInterval("time interval");
    }
    
    private void checkInvalidInterval(String invalidInterval){
        try {
            TimeInterval t;
            t = new TimeInterval(invalidInterval);
            fail("Successfully parsed wrong interval");
        } catch (IllegalValueException e) {
        }
    }
    
}
//@@author