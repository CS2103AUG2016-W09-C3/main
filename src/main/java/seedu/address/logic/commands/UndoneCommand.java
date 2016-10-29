// @@author A0140155U
package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Marks a task in the task list as undone.
 */
public class UndoneCommand extends Command {

    public static final String COMMAND_WORD = "undone";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {};
    
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a task as undone.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Task marked as %1$s: %2$s";
    public static final String MESSAGE_EXCEPTION = "Error executing command.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";
    public static final String MESSAGE_ALREADY_UNDONE_TASK = "Task is already undone.";

    private final int targetIndex;

    /**
     * Convenience constructor using raw values for adding 
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UndoneCommand(int targetIndex) throws IllegalValueException {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        if(!taskToDelete.getDoneFlag().isDone()){
            return new CommandResult(MESSAGE_ALREADY_UNDONE_TASK);
        }
        
        Task toAdd = null;
        try {
            DoneFlag newFlag = new DoneFlag(DoneFlag.NOT_DONE);
            if(!taskToDelete.isDated()){
                toAdd = new Task(taskToDelete.getName(), taskToDelete.getPriority(), taskToDelete.getInformation(), 
                            newFlag, taskToDelete.getTags());
            }else{
                ReadOnlyDatedTask datedTaskToDelete = (ReadOnlyDatedTask) taskToDelete;
                toAdd = new DatedTask(datedTaskToDelete.getName(), datedTaskToDelete.getDateTime(),
                        datedTaskToDelete.getLength(), datedTaskToDelete.getRecurrance(),
                        datedTaskToDelete.getPriority(), datedTaskToDelete.getInformation(), 
                        newFlag, datedTaskToDelete.getTags());
            }
            model.deleteTask(taskToDelete);
            model.addTaskToIndex(toAdd, targetIndex - 1);
        } catch (DuplicateTaskException e) {
            assert false : "Can't add a duplicate task.";
            return new CommandResult(MESSAGE_EXCEPTION);
        } catch (IllegalValueException e) {
            // Should never happen
            assert false : "DoneFlag class is corrupt. Call a programmer.";
            return new CommandResult(MESSAGE_EXCEPTION);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
            return new CommandResult(MESSAGE_EXCEPTION);
        } 
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getDoneFlag().toString(), toAdd.getName()));

    }

    @Override
    public boolean createsNewState() {
        return true;
    }
}
//@@author
