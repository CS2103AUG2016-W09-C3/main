// @@author A0140155U
package guitests;

import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.junit.Test;

import seedu.address.logic.commands.FilepathCommand;
import seedu.address.testutil.TestUtil;

public class FilepathCommandTest extends TaskBookGuiTest {

    @Test
    public void wrongFilePaths() {
        assertFilepath("", false);
        assertFilepath(".xml", false);
        assertFilepath("noxml", false);
        assertFilepath("<>*.xml", false);
        assertFilepath("3:\\data.xml", false);
        assertFilepath(":\\data.xml", false);
        assertFilepath("DRIVE:\\data.xml", false);
        assertFilepath("\\data.xml", false);
        assertFilepath("file\\\\data.xml", false);
        assertFilepath("\\\\file\\\\data.xml", false);
    }
    
    @Test
    public void rightFilePaths() {
        assertFilepath("a.xml", true);
        assertFilepath("hello-world.xml", true);
        assertFilepath("D:\\file.xml", true);
        assertFilepath("folder\\folder\\folder\\file.xml", true);
        assertFilepath("C:\\folder\\folder\\folder\\file.xml", true);
    }
    
    private void assertFilepath(String filePath, boolean result) {
        final Matcher matcher = FilepathCommand.FILEPATH_REGEX.matcher(filePath);
        assert(matcher.matches() == result);
    }

    @Test
    public void noParam() {
        commandBox.runCommand("filepath");
        assertResultMessage(FilepathCommand.MESSAGE_INVALID_PATH);
    }

    @Test
    public void wrongParam() {
        commandBox.runCommand("filepath hello");
        assertResultMessage(FilepathCommand.MESSAGE_INVALID_PATH);
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