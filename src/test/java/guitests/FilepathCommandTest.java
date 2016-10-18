package guitests;

import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.junit.Test;

import seedu.address.logic.commands.FilepathCommand;

public class FilepathCommandTest extends AddressBookGuiTest {

    @Test
    public void wrongFilePaths() {
        assertFilepathFail("");
        assertFilepathFail("noxml");
        assertFilepathFail("<>*.xml");
        assertFilepathFail("3:\\data.xml");
        assertFilepathFail(":\\data.xml");
        assertFilepathFail("DRIVE:\\data.xml");
        assertFilepathFail("\\data.xml");
        assertFilepathFail("file\\\\data.xml");
        assertFilepathFail("\\\\file\\\\data.xml");
    }

    private void assertFilepathFail(String wrongFilePath) {
        final Matcher matcher = FilepathCommand.FILEPATH_REGEX.matcher(wrongFilePath);
        if (matcher.matches()) {
            fail("Wrong file path matched: " + wrongFilePath);
        }
    }

}