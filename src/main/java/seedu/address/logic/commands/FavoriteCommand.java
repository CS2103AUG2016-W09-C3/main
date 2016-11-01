//@@author A0140155U
package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CommandPreset;

/**
 * Adds a favorite command to the preset list.
 */
public class FavoriteCommand extends Command {

    public static final String COMMAND_WORD = "favorite";

    public static final String[] REQUIRED_PARAMS = {"c"};
    public static final String[] POSSIBLE_PARAMS = {"c"};
    
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a favorite command to the preset list.\n"
            + "Parameters: DESCRIPTION c/COMMAND\n"
            + "Example: " + COMMAND_WORD
            + " List all tasks c/list df/all";

    public static final String MESSAGE_SUCCESS = "Added favorite: %1$s";

    private final CommandPreset commandPreset;

    public FavoriteCommand(String command, String description)
            throws IllegalValueException {
        this.commandPreset = new CommandPreset(command, description);
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        model.addPreset(commandPreset);
        return new CommandResult(String.format(MESSAGE_SUCCESS, commandPreset.getCommand()));

    }

    @Override
    public boolean createsNewState() {
        return false;
    }

}
