package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
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
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edit a tasks in the address book.
 */

public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

	public static final String[] REQUIRED_PARAMS = {};

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Edits the task identified by the index number used in the last task listing.\n"
			+ "Parameters: INDEX (must be a positive integer) [n/NAME] [h/TIME d/DATE l/LENGTH] [r/RECUR] [p/PRIORITY] [a/] [i/INFORMATION] [t/TAG]...\n"
			+ "Example: " + COMMAND_WORD + " 1 d/02102016";

	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

	public final int targetIndex;
	
	private Task toAdd;
	private ReadOnlyTask taskToEdit;
	private String name, datetime, length, recurring, priority, information, doneFlag;
	private Set<Tag> tagSet;
	private UniqueTagList tagList;


	public EditCommand(int targetIndex, String name, String datetime, String length, String recurring, 
            String priority, String information, String doneFlag, Set<String> tags) throws IllegalValueException {
		this.targetIndex = targetIndex; 
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
			tagSet.add(new Tag(tagName));
		}
		this.name = name; 
		this.datetime = datetime;
		this.length = length;
		this.recurring = recurring;
		this.priority = priority;
		this.information = information;
		this.doneFlag = doneFlag;
		this.tagSet = tagSet;
	}
	
	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}	
		taskToEdit = lastShownList.get(targetIndex - 1);
		boolean isDated = taskToEdit.isDated();
		
		if(isDated){
			copyDatedTask(lastShownList);
		}
		copyTask();
		editTag();		
		try{
			if(isDated){
				this.toAdd = new DatedTask(
						new Name(name),
				        new DateTime(datetime),
				        new Length(length),
				        new Recurrance(recurring),
				        new Priority(priority),
				        new Information(information),
				        new DoneFlag(doneFlag),
				        new UniqueTagList(tagList)
				);
			} 
			else {
				this.toAdd = new Task(
	                new Name(name),
	                new Priority(priority),
	                new Information(information),
	                new DoneFlag(doneFlag),
	                new UniqueTagList(tagList)
				);
			}
		} catch (IllegalValueException ive) {
			return new CommandResult(ive.getMessage());
		}
		
		try {
			model.deleteTask(taskToEdit);
			model.addTaskToIndex(toAdd, targetIndex-1);
		} catch (TaskNotFoundException e) {
			assert false : "The target task cannot be missing"; }
		 catch (DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}
		
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toAdd));
	}
	
	/**
	 * Copy dated task information of time, date, length, recurring if it is not edited
	 */
	private void copyDatedTask(UnmodifiableObservableList<ReadOnlyTask> lastShownList){
		ReadOnlyDatedTask datedTaskToEdit = (ReadOnlyDatedTask) lastShownList.get(targetIndex - 1);
		if(this.datetime.equals("-1")){
	        this.datetime = datedTaskToEdit.getDateTime().toString();
		}
        if(this.length.equals("-1")){
            this.length = datedTaskToEdit.getLength().toString();
        }
        if(this.recurring.equals(Recurrance.NO_INTERVAL)){
            this.recurring = datedTaskToEdit.getRecurrance().toString();
        }
		/*
		String dateTime = datedTaskToEdit.getDateTime().toString();
		String[] split = dateTime.split("\\s+");
		String date = split[0];
		String time = split[1];
		if(this.time.equals("-1")){
			String[] timeSplit = time.split(":");
			this.time = timeSplit[0] + timeSplit[1];
		}
		if(this.date.equals("-1")){
			String[] dateSplit = date.split("-");
			this.date = dateSplit[0] + dateSplit[1] + dateSplit[2];
		}
		if(this.length.equals("-1")){
			this.length = datedTaskToEdit.getLength().toString();
		}
		if(this.recurring.equals(Recurrance.NO_INTERVAL))
			this.recurring = datedTaskToEdit.getRecurrance().toString();
		*/
//		System.out.println(targetIndex + " " + 	name + " " +  priority + " " + information + " " + doneFlag + " " + time + " "  + date + " " +  length + " " + recurring);
	}
	
	/**
	 * Copy task information of name, priority, information if it is not edited.  
	 */
	private void copyTask(){
		if(this.name.equals(""))
			this.name = taskToEdit.getName().toString();
		if(this.priority.equals(""))
			this.priority = taskToEdit.getPriority().toString();
		if(this.information.equals(""))
			this.information = taskToEdit.getInformation().toString();
	}
	
	/**
	 * Edit the tag of task 
	 */
	private void editTag(){
		if(this.tagSet.isEmpty()){
			tagList = new UniqueTagList(tagSet);
			tagList.setTags(taskToEdit.getTags());
		} else{
			tagList = new UniqueTagList(tagSet);
		}
	}
}
