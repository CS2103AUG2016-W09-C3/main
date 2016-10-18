package seedu.address.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
/**
 * Changes the filepath of the address book.
 */
public class FilepathCommand extends Command {

    public static final String COMMAND_WORD = "filepath";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {};
    
    public static final Pattern FILEPATH_REGEX = Pattern.compile(
            "^([a-zA-Z]\\:\\\\)?[^\\\\:\\*\\|\\\"<>\\?\\n]+(\\\\[^\\\\:\\*\\|\\\"<>\\?\\n]+)*\\.xml$");
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the filepath. "
            + "Parameter: FILEPATH"
            + "Parameter should be a valid file name, ending in .xml."
            + "Examples: addressbook.xml, data\\addressbook.xml, C:\\data\\addressbook.xml";

    public static final String MESSAGE_SUCCESS = "File path changed to %1$s";
    public static final String MESSAGE_INVALID_PATH = "Invalid file path. Parameter should be a valid file name, ending in .xml.\n" + 
                                                    "Examples: addressbook.xml, data\\addressbook.xml, C:\\data\\addressbook.xml";

    private final String filePath;

    public FilepathCommand(String filePath){
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert filePath != null;
        final Matcher matcher = FILEPATH_REGEX.matcher(filePath);
        if (!matcher.matches()) {
            return new CommandResult(MESSAGE_INVALID_PATH);
        }
        model.changeFilePath(filePath);
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));

    }

    @Override
    public boolean createsNewState() {
        return true;
    }

}