//@@author A0139947L
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.model.TaskBook;
import todoit.taskbook.model.task.DateTime;
import todoit.taskbook.model.task.ReadOnlyDatedTask;
import todoit.taskbook.model.task.UniqueTaskList;
import todoit.taskbook.testutil.TestTask;
import todoit.taskbook.testutil.TestUtil;


public class RecurrenceTest extends TaskBookGuiTest {
    
    @Test
    // Updating recurrence
    public void recurrence_checkRecurrenceOnStartUp_Success() {
        TaskBook tasks = getInitialData();
        commandBox.runCommand("done 1");
        
        tasks.updateRecurringTasks();
        assertRecurringSuccess(tasks);
    }
    
    @Test
    // Without updating recurrence
    public void recurrence_checkRecurrenceOnStartUp_Fail() {
        TaskBook tasks = getInitialData();
        commandBox.runCommand("done 1");
  
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