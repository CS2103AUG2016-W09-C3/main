package seedu.address.logic.commands;

import seedu.address.model.AddressBook;

/**
 * Clears the task book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task book has been cleared!";
    //@@author A0139121R
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the task book completely. Parameter: clear\n"
            + "Parameter should not contain anything else other than clear\n"
            + "Example: " + COMMAND_WORD;
    //@@author
    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(AddressBook.getEmptyAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    @Override
    public boolean createsNewState() {
        return true;
    }
}
