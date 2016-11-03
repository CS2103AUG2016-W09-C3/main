//@@author A0139947L
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.model.TaskBook;
import todoit.taskbook.model.task.DateTime;
import todoit.taskbook.model.task.ReadOnlyDatedTask;
import todoit.taskbook.model.task.UniqueTaskList;


public class RecurrenceTest extends TaskBookGuiTest {
    
    @Test
    // Updating recurrence
    public void checkRecurrenceDone() {
        TaskBook tasks = getInitialData();
        
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.meetingToAttend.getAddCommand());
        commandBox.runCommand("done 7");
        
        DateTime csFinalExamToAdd = td.csFinalExam.getDateTime();
        tasks.updateRecurringTasks();
        
        assertFalse(tasks.getUniqueTaskList().getInternalList().get(6).equals(td.csFinalExam) && !td.csFinalExam.getDateTime().equals(csFinalExamToAdd));
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
        
        DateTime csFinalExamToAdd = td.csFinalExam.getDateTime();

        assertFalse(!tasks.getUniqueTaskList().getInternalList().get(6).equals(td.csFinalExam) && !td.csFinalExam.getDateTime().equals(csFinalExamToAdd));   
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