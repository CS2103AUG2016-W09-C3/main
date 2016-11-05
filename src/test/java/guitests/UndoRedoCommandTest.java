// @@author A0140155U
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import todoit.taskbook.commons.core.Messages;
import todoit.taskbook.logic.commands.AddCommand;
import todoit.taskbook.model.StatesManager;
import todoit.taskbook.testutil.TestTask;
import todoit.taskbook.testutil.TestUtil;

public class UndoRedoCommandTest extends TaskBookGuiTest {

    @Test
    public void undo_addCommand_undoSuccessful() {
        //add one task
        TestTask[] initialList = td.getTypicalTasks();
        TestTask taskToAdd = td.nieceBirthdayMeal;
        TestTask[] finalList = TestUtil.addTasksToList(initialList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(initialList));
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    @Test
    public void undoredo_addCommand_allStatesMatch() {
        //add one task
        TestTask[] initialList = td.getTypicalTasks();
        TestTask taskToAdd = td.nieceBirthdayMeal;
        TestTask[] finalList = TestUtil.addTasksToList(initialList, taskToAdd);
        assertUndoRedo(initialList, finalList, new String[] {taskToAdd.getAddCommand()});
    }

    @Test
    public void undoredo_multipleCommands_allStatesMatch() {
        //add one task
        TestTask[] initialList = td.getTypicalTasks();
        TestTask taskToAdd = td.nieceBirthdayMeal;
        TestTask taskToAdd2 = td.cuttingHair;
        TestTask taskToAdd3 = td.lectureToAttend;
        TestTask[] finalList = TestUtil.addTasksToList(initialList, taskToAdd, taskToAdd2, taskToAdd3);
        assertUndoRedo(initialList, finalList, 
                new String[] {taskToAdd.getAddCommand(), taskToAdd2.getAddCommand(), taskToAdd3.getAddCommand()});
    }
    
    @Test
    public void undoredo_clearCommand_allStatesMatch() {
        //add one task
        TestTask[] initialList = td.getTypicalTasks();
        TestTask[] finalList = {};
        assertUndoRedo(initialList, finalList, new String[] {"clear"});
    }
    
    @Test
    public void undo_noPrevState_displayErrorMessage() {
        commandBox.runCommand("undo");
        assertResultMessage(StatesManager.MESSAGE_NO_PREV_STATE);
    }

    @Test
    public void redo_noNextState_displayErrorMessage() {
        commandBox.runCommand("redo");
        assertResultMessage(StatesManager.MESSAGE_NO_NEXT_STATE);
    }
    
    @Test
    public void undo_nonStateCommands_displayErrorMessage() {
        commandBox.runCommand("select 1");
        commandBox.runCommand("find lunch");
        commandBox.runCommand("list");
        commandBox.runCommand("undo");
        assertResultMessage(StatesManager.MESSAGE_NO_PREV_STATE);
    }
    
    /*
     * Takes in the initial task list, a list of commands, and the final task list after all commands have been executed.
     * Does the following:
     * Initial ---Execute all---> Final ---Undo all---> Initial ---Redo all---> Final
     */
    private void assertUndoRedo(TestTask[] initialList, TestTask[] finalList, String... commands) {
        assertTrue(taskListPanel.isListMatching(initialList));
        for(int i = 0; i < commands.length; i++){
            commandBox.runCommand(commands[i]);
        }
        assertTrue(taskListPanel.isListMatching(finalList));
        for(int i = 0; i < commands.length; i++){
            commandBox.runCommand("undo");
        }
        assertTrue(taskListPanel.isListMatching(initialList));
        for(int i = 0; i < commands.length; i++){
            commandBox.runCommand("redo");
        }
        assertTrue(taskListPanel.isListMatching(finalList));
    }

}
//@@author