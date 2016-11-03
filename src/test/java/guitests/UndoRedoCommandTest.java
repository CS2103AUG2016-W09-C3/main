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
    public void addUndoRedo() {
        //add one task
        TestTask[] initialList = td.getTypicalTasks();
        TestTask taskToAdd = td.nieceBirthdayMeal;
        TestTask[] finalList = TestUtil.addTasksToList(initialList, taskToAdd);
        assertUndoRedo(initialList, finalList, new String[] {taskToAdd.getAddCommand()});
    }

    @Test
    public void addUndoRedoMultiple() {
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
    public void clearUndoRedo() {
        //add one task
        TestTask[] initialList = td.getTypicalTasks();
        TestTask[] finalList = {};
        assertUndoRedo(initialList, finalList, new String[] {"clear"});
    }
    
    @Test
    public void noUndo() {
        commandBox.runCommand("undo");
        assertResultMessage(StatesManager.MESSAGE_NO_PREV_STATE);
    }

    @Test
    public void noRedo() {
        commandBox.runCommand("redo");
        assertResultMessage(StatesManager.MESSAGE_NO_NEXT_STATE);
    }
    
    @Test
    public void nonStateCommands() {
        commandBox.runCommand("select 1");
        commandBox.runCommand("find lunch");
        commandBox.runCommand("list");
        commandBox.runCommand("undo");
        assertResultMessage(StatesManager.MESSAGE_NO_PREV_STATE);
    }
    
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