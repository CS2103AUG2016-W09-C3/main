package guitests;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.UniqueTaskList;


public class RecurrenceTest extends AddressBookGuiTest {
    
    @Test
    // Updating recurrence
    public void checkRecurrenceTrue() {
        AddressBook tasks = getInitialData();
        
        if (tasks.getUniqueTaskList().getInternalList().size() > 0) {
            commandBox.runCommand("Done 1");
            tasks.updateRecurringTasks();
        }
        assertRecurringSuccess(tasks);
    }
    
    @Test
    // Without updating recurrence
    public void checkRecurrenceFalse() {
        AddressBook tasks = getInitialData();
        
        if (tasks.getUniqueTaskList().getInternalList().size() > 0) {
            commandBox.runCommand("Done 1");
        }
        assertRecurringSuccess(tasks);
    }

    private void assertRecurringSuccess(AddressBook tasks) {
        // Test and make sure every task that is recurring is undone
        for (int i=0; i<tasks.getUniqueTaskList().getInternalList().size(); i++) {
            if (tasks.getUniqueTaskList().getInternalList().get(i).getDoneFlag().isDone()){
                assert(false);
            }
        }
    }
}