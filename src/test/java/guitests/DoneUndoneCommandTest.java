package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.model.task.DatedTask;
import seedu.address.model.task.DoneFlag;
import seedu.address.model.task.ReadOnlyDatedTask;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_SUCCESS;

public class DoneUndoneCommandTest extends AddressBookGuiTest {


    @Test
    public void doneUndone() {

        //done the first task in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDoneSuccess(targetIndex, currentList);
        currentList = doTask(targetIndex, currentList, DoneFlag.DONE);
        
        //mark the last task in list as done
        targetIndex = currentList.length;
        assertDoneSuccess(targetIndex, currentList);
        currentList = doTask(targetIndex, currentList, DoneFlag.DONE);

        //check already done
        targetIndex = currentList.length;
        commandBox.runCommand("done " + targetIndex);
        assertResultMessage("Task is already done.");

        //undone the last task in the list
        targetIndex = currentList.length;
        assertUndoneSuccess(targetIndex, currentList);
        currentList = doTask(targetIndex, currentList, DoneFlag.NOT_DONE);

        //check already undone
        commandBox.runCommand("undone " + targetIndex);
        assertResultMessage("Task is already undone.");

        //done from the middle of the list
        targetIndex = currentList.length/2;
        assertDoneSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

        commandBox.runCommand("undone " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }


    /**
     * Runs the done command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDo = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = doTask(targetIndexOneIndexed, currentList, DoneFlag.DONE);
        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_SUCCESS, DoneFlag.DONE, taskToDo.getName()));
        try {
            taskToDo.setDoneFlag(new DoneFlag(DoneFlag.NOT_DONE) );
        } catch (IllegalValueException e) {
            assert(false);
        }
    }

    /**
     * Runs the undone command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertUndoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDo = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = doTask(targetIndexOneIndexed, currentList, DoneFlag.NOT_DONE);
        commandBox.runCommand("undone " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_SUCCESS, DoneFlag.NOT_DONE, taskToDo.getName()));
        try {
            taskToDo.setDoneFlag(new DoneFlag(DoneFlag.DONE) );
        } catch (IllegalValueException e) {
            assert(false);
        }
    }
    
    private TestTask[] doTask(int targetIndexOneIndexed, final TestTask[] currentList, String flag) {
        TestTask taskToDo = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        try {
            taskToDo.setDoneFlag(new DoneFlag(flag));
        } catch (IllegalValueException e) {
            assert(false);
        }
        //expectedRemainder = TestUtil.addPersonsToList(expectedRemainder, taskToDo);
        expectedRemainder = TestUtil.addTaskToListIndex(expectedRemainder, taskToDo, targetIndexOneIndexed - 1);
        return expectedRemainder;
    }
  
}
