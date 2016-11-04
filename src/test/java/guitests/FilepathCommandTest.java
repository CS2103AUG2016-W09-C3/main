// @@author A0140155U
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import todoit.taskbook.logic.commands.FilepathCommand;
import todoit.taskbook.testutil.TestUtil;

public class FilepathCommandTest extends TaskBookGuiTest {

    @Test
    public void noParam() {
        commandBox.runCommand("filepath");
        assertResultMessage(FilepathCommand.MESSAGE_MISSING_EXTENSION);
    }

    @Test
    public void noxml() {
        commandBox.runCommand("filepath hello");
        assertResultMessage(FilepathCommand.MESSAGE_MISSING_EXTENSION);
    }
    
    @Test
    public void invalidChars() {
        commandBox.runCommand("filepath <>*.xml");
        assertResultMessage(FilepathCommand.MESSAGE_CANNOT_WRITE);
    }

    @Test
    public void invalidDrive() {
        commandBox.runCommand("filepath IMPOSSIBLEDRIVE:\\data.xml");
        assertResultMessage(FilepathCommand.MESSAGE_CANNOT_WRITE);
    }

    @Test
    public void noname() {
        String filePath = TestUtil.getFilePathInSandboxFolder(".xml");
        assertFilePathChange(filePath);
    }
    
    @Test
    public void simplePath() {
        String filePath = TestUtil.getFilePathInSandboxFolder("data.xml");
        assertFilePathChange(filePath);
    }
    
    @Test
    public void complexPath() {
        String filePath = TestUtil.getFilePathInSandboxFolder("data/data/data.xml");
        assertFilePathChange(filePath);
    }

    private void assertFilePathChange(String filePath) {
        commandBox.runCommand("filepath " + filePath);
        assertEquals(testApp.getSaveFilePath(), filePath);
    }
    
}
//@@author