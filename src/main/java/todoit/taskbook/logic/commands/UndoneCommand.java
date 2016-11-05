// @@author A0140155U
package todoit.taskbook.logic.commands;

import java.util.HashSet;
import java.util.Set;

import todoit.taskbook.commons.core.Messages;
import todoit.taskbook.commons.core.UnmodifiableObservableList;
import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.model.tag.Tag;
import todoit.taskbook.model.tag.UniqueTagList;
import todoit.taskbook.model.task.*;
import todoit.taskbook.model.task.UniqueTaskList.DuplicateTaskException;
import todoit.taskbook.model.task.UniqueTaskList.TaskNotFoundException;

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

        // Check for out of range
        if (targetIndex >= lastShownList.size() || targetIndex < 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        // Get task
        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex);
        if(!taskToDelete.getDoneFlag().isDone()){
            return new CommandResult(MESSAGE_ALREADY_UNDONE_TASK);
        }

        // Modify task
        Task toAdd = null;
        try {
            DoneFlag newFlag = new DoneFlag(DoneFlag.NOT_DONE);
            if(!taskToDelete.isDated()){
                toAdd = new Task(taskToDelete.getName(), taskToDelete.getPriority(), taskToDelete.getInformation(), 
                            newFlag, taskToDelete.getTags());
            }else{
                ReadOnlyDatedTask datedTaskToDelete = (ReadOnlyDatedTask) taskToDelete;
                toAdd = new DatedTask(datedTaskToDelete.getName(), datedTaskToDelete.getDateTime(),
                        datedTaskToDelete.getLength(), datedTaskToDelete.getRecurrence(),
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
