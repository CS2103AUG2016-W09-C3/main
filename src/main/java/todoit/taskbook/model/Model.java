package todoit.taskbook.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import todoit.taskbook.commons.core.UnmodifiableObservableList;
import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.commons.exceptions.StateException;
import todoit.taskbook.model.task.ReadOnlyTask;
import todoit.taskbook.model.task.Task;
import todoit.taskbook.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    //@@author A0139046E
    /** Adds the given task at the specific index*/
    void addTaskToIndex(Task task, int index) throws UniqueTaskList.DuplicateTaskException;
    //@@author
    
    /** Saves the current state onto stack*/
    void saveState(String commandText);
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    //@@author A0139121R  
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords, HashSet<String> searchScope);
    
    /** Sorts the filter of the filtered task list to */
    void updateSortTaskList(HashMap<String, String> dateRange, ArrayList<String> sortByAttribute, String doneStatus, boolean reverse);
    
    /** Updates the filter of the filtered task list to show not done tasks*/
    void updateFilteredListToShowUndone();
    
    /** Updates the filter of the filtered task list to show not done tasks*/
    void updateFilteredListToShowDone();
    //@@author
    // @@author A0140155U
    /** Loads the previous state. Returns the command attached to the state to be printed. */
    String loadPreviousState() throws StateException;

    /** Loads the next state. Returns the command attached to the state to be printed. */
    String loadNextState() throws StateException;
    
    /** Raises event to change file path of data */
    void changeFilePath(String filePath);

    /** Adds a command preset to the current list */
    void addPreset(CommandPreset commandPreset);

    /** Removes a command preset to the current list. Returns the description of the removed preset to be printed. */
    String removePreset(int index) throws IllegalValueException;
    // @@author
}
