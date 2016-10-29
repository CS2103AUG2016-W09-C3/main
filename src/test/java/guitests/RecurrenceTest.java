//@@author A0139947L
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.ReadOnlyDatedTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;


public class RecurrenceTest extends AddressBookGuiTest {
    
    @Test
    // Updating recurrence
    public void checkRecurrenceDone() {
        TaskBook tasks = getInitialData();
        
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.meetingToAttend.getAddCommand());
        commandBox.runCommand("done 7");
        
        DateTime testCase7 = td.csFinalExam.getDateTime();
        tasks.updateRecurringTasks();
        
        assertFalse(tasks.getUniqueTaskList().getInternalList().get(6).equals(td.csFinalExam) && !td.csFinalExam.getDateTime().equals(testCase7));
        assertRecurringSuccess(tasks);
    }
    
    @Test
    // Without updating recurrence
    public void checkRecurrenceFalse() {
        TaskBook tasks = getInitialData();
        
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        commandBox.runCommand(td.meetingToAttend.getAddCommand());
        
        commandBox.runCommand("done 7");
        
        DateTime testCase7 = td.csFinalExam.getDateTime();

        assertFalse(!tasks.getUniqueTaskList().getInternalList().get(6).equals(td.csFinalExam) && !td.csFinalExam.getDateTime().equals(testCase7));   
        assertRecurringSuccess(tasks);
    }

    private void assertRecurringSuccess(TaskBook tasks) {
        // Test and make sure every task that is recurring is undone
        for (int i=0; i<tasks.getUniqueTaskList().getInternalList().size(); i++) {
            if (tasks.getUniqueTaskList().getInternalList().get(i).getDoneFlag().isDone()) {
                assert(false);
            }
        }
        assert(true);
    }
}
//@@author