package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.StateException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a tasks to the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {""};
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous command. ";
    public static final String MESSAGE_SUCCESS = "Undid previous command.";


    /**
     * Convenience constructor using raw values for adding 
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UndoCommand(){
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.loadPreviousState();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (StateException e) {
            return new CommandResult(e.getMessage());
        }

    }

    @Override
    public boolean createsNewState() {
        return false; 
    }
}
