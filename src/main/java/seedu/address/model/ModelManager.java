package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.DatedTask;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Task> filteredTasks;
    private final StateManager states;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(AddressBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new AddressBook(src);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
        states = new StateManager();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyAddressBook initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
        states = new StateManager();
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        states.saveState(addressBook);
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void addTaskToIndex(Task task, int index) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTaskToIndex(task, index);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, HashSet<String> searchScope){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, searchScope)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private HashSet<String> searchScope;

        NameQualifier(Set<String> nameKeyWords, HashSet<String> searchScope) {
            this.nameKeyWords = nameKeyWords;
            this.searchScope = searchScope;
        }
        /**
         * Tests if task contains any of the keywords in nameKeyWords in the possible specified searchScope of "n"(name) "i"(information) 
         * and "d"(date and time).
         */
        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()//takes in task returns true if matches keyword. Converts to stream, check task name contains keyword
                    //.filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .filter(keyword -> (this.searchScope.contains("n") && StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                            || (this.searchScope.contains("i") && StringUtil.containsIgnoreCase(task.getInformation().fullInformation, keyword))
                            || (this.searchScope.contains("d") && task.isDated() && StringUtil.containsIgnoreCase(((DatedTask) task).getDateTime().toString(), keyword))
                            )
                    .findAny()//finds first one
                    .isPresent();//check if null
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
