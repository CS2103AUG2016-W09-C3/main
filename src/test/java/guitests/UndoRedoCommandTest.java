package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.StatesManager;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class UndoRedoCommandTest extends AddressBookGuiTest {

    @Test
    public void addUndoRedo() {
        //add one person
        TestTask[] initialList = td.getTypicalTasks();
        TestTask personToAdd = td.nieceBirthdayMeal;
        TestTask[] finalList = TestUtil.addTasksToList(initialList, personToAdd);
        assertUndoRedo(initialList, finalList, new String[] {personToAdd.getAddCommand()});
    }

    @Test
    public void addUndoRedoMultiple() {
        //add one person
        TestTask[] initialList = td.getTypicalTasks();
        TestTask personToAdd = td.nieceBirthdayMeal;
        TestTask personToAdd2 = td.cuttingHair;
        TestTask personToAdd3 = td.lectureToAttend;
        TestTask[] finalList = TestUtil.addTasksToList(initialList, personToAdd, personToAdd2, personToAdd3);
        assertUndoRedo(initialList, finalList, 
                new String[] {personToAdd.getAddCommand(), personToAdd2.getAddCommand(), personToAdd3.getAddCommand()});
    }
    
    @Test
    public void clearUndoRedo() {
        //add one person
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
