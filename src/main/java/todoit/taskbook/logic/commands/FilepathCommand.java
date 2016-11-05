// @@author A0140155U
package todoit.taskbook.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.commons.util.FileUtil;
/**
 * Changes the filepath of the task book.
 */
public class FilepathCommand extends Command {

    public static final String COMMAND_WORD = "filepath";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {};
    
    public static final String FILE_EXTENSION = ".xml";
            
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the filepath. "
            + "Parameter: FILEPATH"
            + "Parameter should be a valid file path, ending in .xml."
            + "Examples: taskbook.xml, data\\taskbook.xml, C:\\data\\taskbook.xml";

    public static final String MESSAGE_SUCCESS = "File path changed to %1$s";
    public static final String MESSAGE_MISSING_EXTENSION = "Invalid file path. Please check that your filepath ends with " + FILE_EXTENSION + ".";
    public static final String MESSAGE_CANNOT_WRITE = "Unable to write to file. Please check that the filepath is valid, "
                                                    + " and that you have permission to write to the folder.";

    private final String filePath;

    public FilepathCommand(String filePath){
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert filePath != null;
        
        if (!filePath.endsWith(FILE_EXTENSION)) {
            return new CommandResult(MESSAGE_MISSING_EXTENSION);
        }
        
        if(!canCreateFile(filePath)){
            return new CommandResult(MESSAGE_CANNOT_WRITE);
        }
        
        model.changeFilePath(filePath);
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));

    }
    
    /*
     * Checks if a file can be created at specified path by creating a file.
     */
    private boolean canCreateFile(String path){
        try{
            File file = new File(path);
            
            // Delete file if exists
            FileUtil.deleteFile(file);
            
            // Check if file can be created
            FileUtil.createFile(file);
            return true;
        }catch(IOException e){
            return false;
        }
    }

    @Override
    public boolean createsNewState() {
        return true;
    }

}
//@@author