package seedu.address.logic.commands;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the task list completely. Parameter: clear\n"
            + "Parameter should not contain anything else other than clear\n"
            + "Example: " + COMMAND_WORD;

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(AddressBook.getEmptyAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
