// @@author A0140155U
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import todoit.taskbook.logic.commands.FavoriteCommand;
import todoit.taskbook.logic.commands.UnfavoriteCommand;
import todoit.taskbook.testutil.TestPreset;
import todoit.taskbook.testutil.TestTask;
import todoit.taskbook.testutil.TestUtil;

public class FavoriteUnfavoriteCommandTest extends TaskBookGuiTest  {


    private final String INVALID_MESSAGE_FAV = "Invalid command format! \n" + FavoriteCommand.MESSAGE_USAGE;
    private final String INVALID_MESSAGE_UNFAV = "Invalid command format! \n" + UnfavoriteCommand.MESSAGE_USAGE;
    private final String OUT_OF_RANGE_MESSAGE = "Index out of range.";
    private final String BANNED_COMMAND_MESSAGE = "You may not add the 'favorite' or 'unfavorite' command as a preset.";
    
    /*
     * Navigated: navigateToPreset is called to select it
     * Typed: favorite INDEX is called to select it
     */
    @Test
    public void favorite_listCommandNavigated_listAllTasks() {
        commandBox.runCommand("favorite List c/list");
        presetListPanel.navigateToPreset("List");
        TestPreset[] expectedPresets = {new TestPreset("list", "List")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        TestTask[] expectedList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_clearCommandNavigated_clearAllTasks() {
        commandBox.runCommand("favorite Clear c/clear");
        presetListPanel.navigateToPreset("Clear");
        TestPreset[] expectedPresets = {new TestPreset("clear", "Clear")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        TestTask[] expectedList = {};
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_addCommandNavigated_addNewTask() {
        TestTask personToAdd = td.nieceBirthdayMeal;
        commandBox.runCommand("favorite Add c/" + personToAdd.getAddCommand());
        TestPreset[] expectedPresets = {new TestPreset(personToAdd.getAddCommand(), "Add")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        presetListPanel.navigateToPreset("Add");
        TestTask[] expectedList = TestUtil.addTasksToList(td.getTypicalTasks(), personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    @Test
    public void favorite_addCommandTyped_addNewTask() {
        TestTask personToAdd = td.nieceBirthdayMeal;
        commandBox.runCommand("favorite Add c/" + personToAdd.getAddCommand());
        TestPreset[] expectedPresets = {new TestPreset(personToAdd.getAddCommand(), "Add")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        commandBox.runCommand("favorite 1");
        TestTask[] expectedList = TestUtil.addTasksToList(td.getTypicalTasks(), personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_multipleCommandsNavigated_executeAllCommands() {
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
    public void favorite_multipleCommandsTyped_executeAllCommands() {
        commandBox.runCommand("favorite Clear c/clear");
        commandBox.runCommand("favorite Undo c/undo");
        commandBox.runCommand("favorite Redo c/redo");
        TestPreset[] expectedPresets = {new TestPreset("clear", "Clear"),
                new TestPreset("undo", "Undo"),
                new TestPreset("redo", "Redo")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        commandBox.runCommand("favorite 1");
        TestTask[] expectedList = {};
        assertTrue(taskListPanel.isListMatching(expectedList));
        commandBox.runCommand("favorite 2");
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        commandBox.runCommand("favorite 3");
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    @Test
    public void favorite_invalidParams_displayInvalidMessages() {
        commandBox.runCommand("favorite c/no desc");
        assertResultMessage(INVALID_MESSAGE_FAV);
        commandBox.runCommand("favorite no command");
        assertResultMessage(INVALID_MESSAGE_FAV);
        commandBox.runCommand("favorite");
        assertResultMessage(INVALID_MESSAGE_FAV);
    }

    @Test
    public void favorite_bannedCommands_displayBannedMessage() {
        commandBox.runCommand("favorite Favorite c/favorite 1");
        assertResultMessage(BANNED_COMMAND_MESSAGE);
        commandBox.runCommand("favorite Favorite c/FAVORITE 1");
        assertResultMessage(BANNED_COMMAND_MESSAGE);
        commandBox.runCommand("favorite Favorite c/   favorite 1");
        assertResultMessage(BANNED_COMMAND_MESSAGE);
        commandBox.runCommand("favorite Favorite c/unfavorite 1");
        assertResultMessage(BANNED_COMMAND_MESSAGE);
    }
    
    @Test
    public void favorite_selectInvalidIndex_displayOutOfRangeMessage() {
        commandBox.runCommand("favorite 100");
        assertResultMessage(OUT_OF_RANGE_MESSAGE);
    }
    
    @Test
    public void favoriteUnfavorite_oneCommand_addAndRemovesPresets() {
        commandBox.runCommand("favorite List c/list");
        TestPreset[] expectedPresets = {new TestPreset("list", "List")};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
        commandBox.runCommand("unfavorite 1");
        expectedPresets = new TestPreset[] {};
        assertTrue(presetListPanel.isListMatching(expectedPresets));
    }
    
    @Test
    public void favoriteUnfavorite_multipleCommands_addAndRemovesPresets() {
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
    public void unfavorite_invalidParams_displayInvalidMessages() {
        commandBox.runCommand("unfavorite");
        assertResultMessage(INVALID_MESSAGE_UNFAV);
        commandBox.runCommand("unfavorite 1");
        assertResultMessage(OUT_OF_RANGE_MESSAGE);
    }
    
}
//@@author
