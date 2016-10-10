package guitests;

import guitests.guihandles.PersonCardHandle;
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

public class DoneCommandTest extends AddressBookGuiTest {


    @Test
    public void done() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalPersons();
        int targetIndex = 1;
        assertDoneSuccess(targetIndex, currentList, true);
        currentList = doTask(targetIndex, currentList);
        
        //delete the last in the list
        targetIndex = currentList.length;
        assertDoneSuccess(targetIndex, currentList, false);
        currentList = doTask(targetIndex, currentList);

        //delete from the middle of the list
        targetIndex = currentList.length/2;
        assertDoneSuccess(targetIndex, currentList, true);

        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the done command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList, boolean isDone) {
        TestTask taskToDo = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = doTask(targetIndexOneIndexed, currentList);
        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(personListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_SUCCESS, isDone ? DoneFlag.DONE : DoneFlag.NOT_DONE, taskToDo.getName()));
        taskToDo.setDoneFlag(taskToDo.getDoneFlag().flip());
    }

    private TestTask[] doTask(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDo = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removePersonFromList(currentList, targetIndexOneIndexed);
        taskToDo.setDoneFlag(taskToDo.getDoneFlag().flip());
        expectedRemainder = TestUtil.addPersonsToList(expectedRemainder, taskToDo);
        return expectedRemainder;
    }
  
}
