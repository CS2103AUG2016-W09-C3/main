package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.StateException;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

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
    /** Loads the previous state. Returns the command attached to the state to be printed. */
    String loadPreviousState() throws StateException;

    /** Loads the next state. Returns the command attached to the state to be printed. */
    String loadNextState() throws StateException;

    void changeFilePath(String filePath);

}
