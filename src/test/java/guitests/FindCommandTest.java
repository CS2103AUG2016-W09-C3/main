package guitests;

import org.junit.Test;

import todoit.taskbook.commons.core.Messages;
import todoit.taskbook.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskBookGuiTest {
    //@@author A0139121R
    @Test
    public void find_afterDelete_deletedTaskNotDisplayed() {
        assertFindResult("find Meier", td.breadShopping, td.danielLunch); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier",td.danielLunch);
    }

    @Test
    public void find_nonexistantString_noResults() {
        assertFindResult("find Mark"); //no results
    }
    
    @Test
    public void find_emptyTasklist_noResults(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_byName_oneResult(){
        assertFindResult("find Meier s/name", td.danielLunch);
    }
    
    @Test
    public void find_byTag_oneResult(){
        assertFindResult("find boss s/tag", td.aliceMeeting);
    }
    
    @Test
    public void find_byInformation_oneResult(){
        assertFindResult("find loan s/information", td.lorryMaintainance);
    }
    //@@author
    @Test
    public void find_invalidCommand_displayErrorMessage() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
