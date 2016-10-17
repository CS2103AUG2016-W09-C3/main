package seedu.address.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class TimeIntervalTest {

    @Test
    public void checkValidIntervals() {
        try {
            TimeInterval t;
            t = new TimeInterval("10m");
            assertEquals(t.getAsMinutes(), 10);
            t = new TimeInterval("4h");
            assertEquals(t.getAsHours(), 4);
            t = new TimeInterval("40h");
            assertEquals(t.getAsHours(), 40);
            t = new TimeInterval("2d");
            assertEquals(t.getAsHours(), 2 * 24);
            t = new TimeInterval("3w");
            assertEquals(t.getAsHours(), 3 * 7 * 24);
        } catch (IllegalValueException e) {
            fail("Could not parse time interval");
        }
    }
    @Test
    public void checkInvalidIntervals() {
        checkInvalidInterval("1");
        checkInvalidInterval("1hr");
        checkInvalidInterval("2weeks");
        checkInvalidInterval("1 h");
        checkInvalidInterval("0.5h");
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
