package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.StateException;

/**
 * Adds a tasks to the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {""};
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes a command that was undone. ";
    public static final String MESSAGE_SUCCESS = "Redid previous command \"%1$s\"";


    /**
     * Convenience constructor using raw values for adding 
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public RedoCommand(){
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            String commandString = model.loadNextState();
            return new CommandResult(String.format(MESSAGE_SUCCESS, commandString));
        } catch (StateException e) {
            return new CommandResult(e.getMessage());
        }

    }

    @Override
    public boolean createsNewState() {
        return false; 
    }
}
