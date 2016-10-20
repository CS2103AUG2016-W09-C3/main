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
            assertEquals(t.getAsMinutes(), 4 * 60);
            t = new TimeInterval("40h");
            assertEquals(t.getAsMinutes(), 40 * 60);
            t = new TimeInterval("2d");
            assertEquals(t.getAsMinutes(), 2 * 24 * 60);
            t = new TimeInterval("3w");
            assertEquals(t.getAsMinutes(), 3 * 7 * 24 * 60);
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
