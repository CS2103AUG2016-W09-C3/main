// @@author A0140155U
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class FavoriteCommandTest extends TaskBookGuiTest  {


    private final String INVALID_MESSAGE = "Invalid command format! \n" + FavoriteCommand.MESSAGE_USAGE;
    
    @Test
    public void list() {
        commandBox.runCommand("favorite List c/list");
        presetListPanel.navigateToPreset("List");
        TestTask[] expectedList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void clear() {
        commandBox.runCommand("favorite Clear c/clear");
        presetListPanel.navigateToPreset("Clear");
        TestTask[] expectedList = {};
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void add() {
        TestTask taskToAdd = td.nieceBirthdayMeal;
        commandBox.runCommand("favorite Add c/" + taskToAdd.getAddCommand());
        presetListPanel.navigateToPreset("Add");
        TestTask[] expectedList = TestUtil.addTasksToList(td.getTypicalTasks(), taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void multipleCommands() {
        commandBox.runCommand("favorite Clear c/clear");
        commandBox.runCommand("favorite Undo c/undo");
        commandBox.runCommand("favorite Redo c/redo");
        presetListPanel.navigateToPreset("Clear");
        TestTask[] expectedList = {};
        assertTrue(taskListPanel.isListMatching(expectedList));
        presetListPanel.navigateToPreset("Undo");
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        presetListPanel.navigateToPreset("Redo");
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void missingParams() {
        commandBox.runCommand("favorite c/no desc");
        assertResultMessage(INVALID_MESSAGE);
        commandBox.runCommand("favorite no command");
        assertResultMessage(INVALID_MESSAGE);
    }
    
    
    
}
//@@author
