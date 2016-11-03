// @@author A0140155U
package todoit.taskbook.logic.commands;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.commons.exceptions.StateException;

/**
 * Adds a tasks to the task book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {""};
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes a command that was undone. ";
    public static final String MESSAGE_SUCCESS = "Redid previous command \"%1$s\"";

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
//@@author