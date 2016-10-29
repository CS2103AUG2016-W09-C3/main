// @@author A0140155U
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.model.CommandPreset;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class FavoriteUnfavoriteCommandTest extends TaskBookGuiTest  {


    private final String INVALID_MESSAGE = "Invalid command format! \n" + FavoriteCommand.MESSAGE_USAGE;
    private final String OUT_OF_RANGE_MESSAGE = "Index out of range.";
    
    @Test
    public void favorite_list() {
        commandBox.runCommand("favorite List c/list");
        presetListPanel.navigateToPreset("List");
        CommandPreset[] expectedPresets = {new CommandPreset("list", "List")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        TestTask[] expectedList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_clear() {
        commandBox.runCommand("favorite Clear c/clear");
        presetListPanel.navigateToPreset("Clear");
        TestTask[] expectedList = {};
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_add() {
        TestTask personToAdd = td.nieceBirthdayMeal;
        commandBox.runCommand("favorite Add c/" + personToAdd.getAddCommand());
        presetListPanel.navigateToPreset("Add");
        TestTask[] expectedList = TestUtil.addTasksToList(td.getTypicalTasks(), personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_multipleCommands() {
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
    public void favorite_missingParams() {
        commandBox.runCommand("favorite c/no desc");
        assertResultMessage(INVALID_MESSAGE);
        commandBox.runCommand("favorite no command");
        assertResultMessage(INVALID_MESSAGE);
    }
    
    @Test
    public void favorite_unfavorite() {
        commandBox.runCommand("favorite List c/list");
        commandBox.runCommand("unfavorite 1");
        CommandPreset[] expectedList = {};
        assertTrue(presetListPanel.isListMatching(expectedList));
    }
    
    
}
//@@author
