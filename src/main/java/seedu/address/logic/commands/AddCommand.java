package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a tasks to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {"d", "l", "r", "p", "i", "t"};
    
    public static final String[] DATED_TASK_PARAMS = {"d", "l"};
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: NAME [d/DATE,TIME l/LENGTH] [r/RECUR] [p/PRIORITY] [a/] [i/INFORMATION] [t/TAG]...\n"
            + "Parameters should not contain '/'s."
            + "Example: " + COMMAND_WORD
            + " Meet John d/2pm next thurs l/2h r/1d p/medium i/Meeting for lunch";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values for adding 
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String datetime, String length, String recurring, 
                      String priority, String information, String doneFlag, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = (Task) new DatedTask(
                new Name(name),
                new DateTime(datetime),
                new Length(length),
                new Recurrance(recurring),
                new Priority(priority),
                new Information(information),
                new DoneFlag(doneFlag),
                new UniqueTagList(tagSet)
        );
    }

    public AddCommand(String name, String priority, String information, String doneFlag, Set<String> tagsFromArgs) 
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagsFromArgs) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Priority(priority),
                new Information(information),
                new DoneFlag(doneFlag),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean createsNewState() {
        return true;
    }

}
