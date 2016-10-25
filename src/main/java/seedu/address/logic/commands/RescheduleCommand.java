//@@author A0139046E
package seedu.address.logic.commands;

import java.time.LocalDateTime;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DateParser;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.DatedTask;
import seedu.address.model.task.DoneFlag;
import seedu.address.model.task.Information;
import seedu.address.model.task.Length;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyDatedTask;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Recurrance;
import seedu.address.model.task.Task;
import seedu.address.model.task.TimeInterval;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Reschedule a task identified using it's last displayed index listing
 */
public class RescheduleCommand extends Command{
	
	public static final String COMMAND_WORD = "reschedule";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Reschedule the task identified by the index number used in the last task listing.\n"
			+ "Parameters: INDEX (must be a positive integer) [INTERVAL]\n"
			+ "Example: " + COMMAND_WORD + " 1 1hr";
	
	public static final String MESSAGE_RESCHEDULE_TASK_SUCCESS = "Rescheduled Task: %1$s";
	
	public static final String MESSAGE_TASK_NOT_DATED = "This is not a dated task.";
	
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";
	
	public final int targetIndex;
	public String interval; 
	public Task toAdd;
	
	public RescheduleCommand(int targetIndex, String interval) throws IllegalValueException{
		this.targetIndex = targetIndex;
		this.interval = interval;
	}
	
	@Override
	public CommandResult execute(){
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask task = lastShownList.get(targetIndex - 1);
        
        if(!task.isDated()){
        	return new CommandResult(String.format(MESSAGE_TASK_NOT_DATED));
        }
       
        ReadOnlyDatedTask taskToReschedule = (ReadOnlyDatedTask) lastShownList.get(targetIndex - 1);
        DateTime toBeEdited = taskToReschedule.getDateTime();
        try {
			LocalDateTime editedLocalDateTime = DateParser.rescheduleDate(toBeEdited.datetime, interval);
			toBeEdited = new DateTime(editedLocalDateTime);
		} catch (IllegalValueException e) {
			return new CommandResult(TimeInterval.MESSAGE_TIME_INTERVAL_CONSTRAINTS);
		}
        
        this.toAdd = new DatedTask(taskToReschedule.getName(),
		        toBeEdited,
		        taskToReschedule.getLength(),
		        taskToReschedule.getRecurrance(),
		        taskToReschedule.getPriority(),
		        taskToReschedule.getInformation(),
		        taskToReschedule.getDoneFlag(),
		        taskToReschedule.getTags()
		        );
        
        try {
            model.deleteTask(taskToReschedule);
            model.addTaskToIndex(toAdd, targetIndex - 1);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}
        
		return new CommandResult(String.format(MESSAGE_RESCHEDULE_TASK_SUCCESS, toAdd));
	}

	@Override
	public boolean createsNewState() {
		return true;
	}
}
//@@author