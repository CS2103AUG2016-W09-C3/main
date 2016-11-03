// @@author A0140155U
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.testutil.TestPreset;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import todoit.taskbook.logic.commands.FavoriteCommand;
import todoit.taskbook.logic.commands.UnfavoriteCommand;

public class FavoriteUnfavoriteCommandTest extends TaskBookGuiTest  {


    private final String INVALID_MESSAGE_FAV = "Invalid command format! \n" + FavoriteCommand.MESSAGE_USAGE;
    private final String INVALID_MESSAGE_UNFAV = "Invalid command format! \n" + UnfavoriteCommand.MESSAGE_USAGE;
    private final String OUT_OF_RANGE_MESSAGE = "Index out of range.";
    
    @Test
    public void favorite_list() {
        commandBox.runCommand("favorite List c/list");
        presetListPanel.navigateToPreset("List");
        TestPreset[] expectedPresets = {new TestPreset("list", "List")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        TestTask[] expectedList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_clear() {
        commandBox.runCommand("favorite Clear c/clear");
        presetListPanel.navigateToPreset("Clear");
        TestPreset[] expectedPresets = {new TestPreset("clear", "Clear")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        TestTask[] expectedList = {};
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_add() {
        TestTask personToAdd = td.nieceBirthdayMeal;
        commandBox.runCommand("favorite Add c/" + personToAdd.getAddCommand());
        TestPreset[] expectedPresets = {new TestPreset(personToAdd.getAddCommand(), "Add")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        presetListPanel.navigateToPreset("Add");
        TestTask[] expectedList = TestUtil.addTasksToList(td.getTypicalTasks(), personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_multipleCommands() {
        commandBox.runCommand("favorite Clear c/clear");
        commandBox.runCommand("favorite Undo c/undo");
        commandBox.runCommand("favorite Redo c/redo");
        TestPreset[] expectedPresets = {new TestPreset("clear", "Clear"),
                new TestPreset("undo", "Undo"),
                new TestPreset("redo", "Redo")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        presetListPanel.navigateToPreset("Clear");
        TestTask[] expectedList = {};
        assertTrue(taskListPanel.isListMatching(expectedList));
        presetListPanel.navigateToPreset("Undo");
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        presetListPanel.navigateToPreset("Redo");
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_invalid() {
        commandBox.runCommand("favorite c/no desc");
        assertResultMessage(INVALID_MESSAGE_FAV);
        commandBox.runCommand("favorite no command");
        assertResultMessage(INVALID_MESSAGE_FAV);
    }
    
    @Test
    public void favorite_unfavorite() {
        commandBox.runCommand("favorite List c/list");
        commandBox.runCommand("unfavorite 1");
        TestPreset[] expectedList = {};
        assertTrue(presetListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_unfavorite_multiple() {
        commandBox.runCommand("favorite Redo c/redo");
        commandBox.runCommand("favorite Clear c/clear");
        commandBox.runCommand("favorite Undo c/undo");
        TestPreset[] expectedPresets = {new TestPreset("redo", "Redo"),
                new TestPreset("clear", "Clear"),
                new TestPreset("undo", "Undo")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        commandBox.runCommand("unfavorite 1");
        expectedPresets = new TestPreset[] {new TestPreset("clear", "Clear"),
                new TestPreset("undo", "Undo")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        commandBox.runCommand("unfavorite 2");
        expectedPresets = new TestPreset[] {new TestPreset("clear", "Clear")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
    }
    
    @Test
    public void unfavorite_invalid() {
        commandBox.runCommand("unfavorite");
        assertResultMessage(INVALID_MESSAGE_UNFAV);
        commandBox.runCommand("unfavorite 1");
        assertResultMessage(OUT_OF_RANGE_MESSAGE);
    }
    
}
//@@author
