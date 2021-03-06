package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import todoit.taskbook.commons.core.Messages;
import todoit.taskbook.logic.commands.AddCommand;
import todoit.taskbook.testutil.TestDatedTask;
import todoit.taskbook.testutil.TestTask;
import todoit.taskbook.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskBookGuiTest {
    //@@author A0139121R
    @Test
    public void add_addTwoTasksOneAtATime_successfullyAddBothTaskWithSuccessMessageShown() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.nieceBirthdayMeal;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.surveyResults;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
    }
    
    @Test
    public void add_addOneDatedTask_successMessageShown(){
        commandBox.runCommand("clear");
        TestDatedTask[] finalList = new TestDatedTask[1];
        finalList[0] = td.lectureToAttend;
        String command = td.lectureToAttend.getAddCommand();
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    @Test
    public void add_invalidAddCommandParameters_UnknownCommandMessageShown(){
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    @Test
    public void add_addToListContainingDuplicateTask_DuplicateTaskMessageShownNothingAdded(){
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand(td.breadShopping.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
    }
    
    @Test
    public void add_addToEmptyList_oneTaskAdded(){
        commandBox.runCommand("clear");
        assertAddSuccess(td.aliceMeeting);
    }
    //@@author
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
