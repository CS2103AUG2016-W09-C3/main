//@@author A0139947L
package guitests;

import org.junit.Test;

import todoit.taskbook.model.TaskBook;

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