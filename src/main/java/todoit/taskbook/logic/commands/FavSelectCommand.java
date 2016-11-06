//@@author A0140155U
package todoit.taskbook.logic.commands;

import todoit.taskbook.commons.exceptions.IllegalValueException;

/**
 * Selects a favorite command from the preset list.
 * 
 * This command uses the exact same keyword as favorite. As such, this command is chosen
 * when the c/ param is NOT provided.
 */
public class FavSelectCommand extends Command {

    public static final String COMMAND_WORD = "favorite";

    public static final String[] REQUIRED_PARAMS = {""};
    public static final String[] POSSIBLE_PARAMS = {""};
    
    // Note: This is NOT USED because the help message for favorite will be printed.
    /*
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a preset from the preset list.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD
            + " 3\n";
     */
    public static final String MESSAGE_OUT_OF_RANGE = "Index out of range.";
    
    // Message success is blank because if the preset switched successfully, the command box will
    // automatically be overwritten by the new command text
    public static final String MESSAGE_SUCCESS = "";

    private final int presetIndex;


    public FavSelectCommand(int presetIndex) {
        this.presetIndex = presetIndex;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.selectPreset(presetIndex);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IllegalValueException e) {
            return new CommandResult(MESSAGE_OUT_OF_RANGE);
        }
    }

    @Override
    public boolean createsNewState() {
        return false;
    }

}
