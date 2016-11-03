//@@author A0140155U
package todoit.taskbook.logic.commands;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.model.CommandPreset;

/**
 * Adds a favorite command to the preset list.
 */
public class UnfavoriteCommand extends Command {

    public static final String COMMAND_WORD = "unfavorite";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {};
    
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a favorite command to the preset list.\n"
            + "Parameters: INDEX";

    public static final String MESSAGE_SUCCESS = "Removed favorite: %1$s";
    public static final String MESSAGE_OUT_OF_RANGE = "Index out of range.";

    private final int index;

    public UnfavoriteCommand(int index)
            throws IllegalValueException {
        this.index = index - 1;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            String removedCommandDesc = model.removePreset(index);
            return new CommandResult(String.format(MESSAGE_SUCCESS, removedCommandDesc));
        } catch (IllegalValueException e) {
            return new CommandResult(MESSAGE_OUT_OF_RANGE);
        }

    }

    @Override
    public boolean createsNewState() {
        return false;
    }

}
