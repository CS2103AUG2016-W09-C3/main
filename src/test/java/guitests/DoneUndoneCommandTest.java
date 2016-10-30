// @@author A0140155U
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

public class DoneUndoneCommandTest extends TaskBookGuiTest {


    @Test
    public void doneFirstTask() {
        assertDoneSuccess(1, td.getTypicalTasks());
    }
    
    @Test
    public void doneLastTask() {
        assertDoneSuccess(td.getTypicalTasks().length, td.getTypicalTasks());
    }
    
    @Test
    public void doneMiddleTask() {
        assertDoneSuccess(td.getTypicalTasks().length / 2, td.getTypicalTasks());
    }
    
    @Test
    public void doneDoneTask() {
        commandBox.runCommand("done 1");
        commandBox.runCommand("done 1");
        assertResultMessage("Task is already done.");
    }
    
    @Test
    public void doneInvalidTask() {
        commandBox.runCommand("done " + td.getTypicalTasks().length + 1);
        assertResultMessage("The task index provided is invalid");
    }
    
    @Test
    public void undoneFirstTask() {
        int targetIndex = 1;
        commandBox.runCommand("done " + targetIndex);
        assertUndoneSuccess(targetIndex, td.getTypicalTasks());
    }
    
    @Test
    public void undoneMiddleTask() {
        int targetIndex = td.getTypicalTasks().length / 2;
        commandBox.runCommand("done " + targetIndex);
        assertUndoneSuccess(targetIndex, td.getTypicalTasks());
    }
    
    @Test
    public void undoneLastTask() {
        int targetIndex = td.getTypicalTasks().length;
        commandBox.runCommand("done " + targetIndex);
        assertUndoneSuccess(targetIndex, td.getTypicalTasks());
    }

    @Test
    public void undoneUndoneTask() {
        commandBox.runCommand("undone 1");
        assertResultMessage("Task is already undone.");
    }

    @Test
    public void undoneInvalidTask() {
        commandBox.runCommand("undone " + td.getTypicalTasks().length + 1);
        assertResultMessage("The task index provided is invalid");
    }
    
    /**
     * Runs the done command to mark doneFlag of the task at specified index as done and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list as done, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before command).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        assertCommandSuccess(targetIndexOneIndexed, currentList, "done", DoneFlag.DONE);
    }

    /**
     * Runs the undone command to mark doneFlag of the task at specified index as undone and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list as undone, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before command).
     */
    private void assertUndoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        assertCommandSuccess(targetIndexOneIndexed, currentList, "undone", DoneFlag.NOT_DONE);
    }
    
    private void assertCommandSuccess(int targetIndexOneIndexed, final TestTask[] currentList, String command, String doneFlag) {
        TestTask taskToDo = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedResult = doTask(targetIndexOneIndexed, currentList, doneFlag);
        commandBox.runCommand(command + " " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedResult));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_SUCCESS, doneFlag, taskToDo.getName()));
    }

    
    private TestTask[] doTask(int targetIndexOneIndexed, final TestTask[] currentList, String flag) {
        TestTask taskToDo = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        try {
            taskToDo.setDoneFlag(new DoneFlag(flag));
        } catch (IllegalValueException e) {
            assert(false);
        }
        //expectedRemainder = TestUtil.addTasksToList(expectedRemainder, taskToDo);
        expectedRemainder = TestUtil.addTaskToListIndex(expectedRemainder, taskToDo, targetIndexOneIndexed - 1);
        return expectedRemainder;
    }
  
}
//@@author