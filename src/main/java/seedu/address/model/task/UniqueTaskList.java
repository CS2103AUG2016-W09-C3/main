package seedu.address.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;

import java.time.LocalDateTime;
import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would
     * fail because there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {
    }

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {
    }

    /**
     * Returns true if the list contains an equivalent task as the given
     * argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task in the
     *             list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    
    //@@author A0139046E
    /**
     * Adds a task to the list at the specific index
     *
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task in the
     *             list.
     */
    public void addToIndex(Task toAdd, int index) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(index, toAdd);
    }
    //@@author

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException
     *             if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTaskList) other).internalList));
    }
    
    //@@author A0139947L
    public void updateRecurringTasks() {

        int size = internalList.size();

        for (int i = 0; i < size; i++) {
            Task recurringTask = internalList.get(i);
            if (!recurringTask.isDated()) {
                // Not dated, so should not have recurring task
                continue;
            } else {
                ReadOnlyDatedTask task = (ReadOnlyDatedTask) recurringTask;
                Recurrance recurrence = task.getRecurrance();
                if (recurrence.toString().equals(recurrence.NO_INTERVAL)) {
                    // No recurring inputs
                    continue;
                } else {
                    // Set DoneFlag to NOT_DONE if it is DONE
                    if (task.getDoneFlag().isDone()) {
                        // setDateAndTime and DONE_FLAG to correct task
                        try {
                            updateTask(i, task, recurrence);
                        } catch (IllegalValueException e) {
                            // Should never happen
                            System.out.println("This should not happen! Please notify a programmer");
                        }
                    }
                }
            }
        }
    }

    private void updateTask(int i, ReadOnlyDatedTask task, Recurrance recurrence) throws IllegalValueException {
        DoneFlag newFlag;
        newFlag = new DoneFlag(DoneFlag.NOT_DONE);

        DateTime dateTime = task.getDateTime();
        String recurr = recurrence.toString();
        
        // Set increment of time & date
        LocalDateTime editDateTime = DateParser.rescheduleDate(dateTime.getDateTime(), recurr);
        
        DateTime latestDateTime = new DateTime(editDateTime);
        
        Task toAdd = null;
        toAdd = new DatedTask(task.getName(), latestDateTime, task.getLength(),
                task.getRecurrance(), task.getPriority(), task.getInformation(), newFlag,
                task.getTags());
        
        internalList.remove(i);
        internalList.add(i, toAdd);
    }
    //@@author

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
    //@@author A0139121R
    public void sortTasks(ArrayList<String> sortByAttribute, boolean reverse) {
        internalList.sort(new CustomTaskComparator(sortByAttribute));
        if (reverse) {
            Collections.reverse(internalList);
        }

    }
    //@@author
}
