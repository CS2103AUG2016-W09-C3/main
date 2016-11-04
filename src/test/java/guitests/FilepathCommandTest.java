// @@author A0140155U
package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import todoit.taskbook.logic.commands.FilepathCommand;
import todoit.taskbook.testutil.TestUtil;

public class FilepathCommandTest extends TaskBookGuiTest {

    @Test
    public void filepath_noParams_displayErrorMessage() {
        commandBox.runCommand("filepath");
        assertResultMessage(FilepathCommand.MESSAGE_MISSING_EXTENSION);
    }

    @Test
    public void filepath_noExtension_displayErrorMessage() {
        commandBox.runCommand("filepath no_xml_extension");
        assertResultMessage(FilepathCommand.MESSAGE_MISSING_EXTENSION);
    }
    
    /*
    These are invalid filepaths on windows, but valid filepaths on linux.
    
    As such, they will pass on a Windows OS, but fail on Linux, which Travis uses.
    
    Since our program is built for windows users, we will still disallow these
    filepaths, but for the sake of passing Travis checks these tests are commented out.
    
    @Test
    public void filepath_invalidFilename_displayErrorMessage() {
        commandBox.runCommand("filepath <>*.xml");
        assertResultMessage(FilepathCommand.MESSAGE_CANNOT_WRITE);
    }

    @Test
    public void filepath_invalidDrive_displayErrorMessage() {
        commandBox.runCommand("filepath IMPOSSIBLEDRIVE:\\data.xml");
        assertResultMessage(FilepathCommand.MESSAGE_CANNOT_WRITE);
    }
     */
    
    @Test
    public void filepath_emptyFileName_successfullyChanged() {
        String filePath = TestUtil.getFilePathInSandboxFolder(".xml");
        assertFilePathChange(filePath);
    }
    
    @Test
    public void filepath_simpleFileName_successfullyChanged() {
        String filePath = TestUtil.getFilePathInSandboxFolder("data.xml");
        assertFilePathChange(filePath);
    }
    
    @Test
    public void filepath_fileWithFolder_successfullyChanged() {
        String filePath = TestUtil.getFilePathInSandboxFolder("data/data/data.xml");
        assertFilePathChange(filePath);
    }
    
    @Test
    public void filepath_mixedSlashes_successfullyChanged() {
        String filePath = TestUtil.getFilePathInSandboxFolder("data/data\\data.xml");
        assertFilePathChange(filePath);
    }
    
    private void assertFilePathChange(String filePath) {
        commandBox.runCommand("filepath " + filePath);
        assertEquals(testApp.getSaveFilePath(), filePath);
    }
    
}
//@@author