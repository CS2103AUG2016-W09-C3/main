// @@author A0140155U
package todoit.taskbook.logic.commands;

import todoit.taskbook.commons.exceptions.StateException;

/**
 * Undoes a command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {""};
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous command. ";
    public static final String MESSAGE_SUCCESS = "Undid previous command \"%1$s\"";

    public UndoCommand(){
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            String commandString = model.loadPreviousState();
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
