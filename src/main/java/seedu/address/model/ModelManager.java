package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.DateParser;
import seedu.address.model.task.DatedTask;
import seedu.address.model.task.DoneFlag;
import seedu.address.model.task.ReadOnlyDatedTask;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.CustomTaskComparator;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.model.FilePathChangedEvent;
import seedu.address.commons.events.model.PresetChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.StateException;
import seedu.address.commons.core.ComponentManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook addressBook;
    private final FilteredList<Task> filteredTasks;
    // @@author A0140155U
    private final States states;
    private final UserPrefs userPrefs;
    // @@author
    /**
     * Initializes a ModelManager with the given TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new TaskBook(src);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
        // @@author A0140155U
        states = new StatesManager(new TaskBookState(addressBook));
        this.userPrefs = userPrefs;
        // @@author
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        addressBook = new TaskBook(initialData);
        //@@author A0139947L
        addressBook.updateRecurringTasks();
        //@@author
        filteredTasks = new FilteredList<>(addressBook.getTasks());
        // @@author A0140155U
        states = new StatesManager(new TaskBookState(addressBook));
        this.userPrefs = userPrefs;
        // @@author
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskBookChangedEvent(addressBook));
    }

    // @@author A0140155U
    /** Raises an event to indicate the config has changed */
    @Override
    public void changeFilePath(String filePath) {
        raise(new FilePathChangedEvent(filePath));
    }
    // @@author
    
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

    // @@author A0140155U
    @Override
    public void addPreset(CommandPreset commandPreset) {
        userPrefs.addPreset(commandPreset);
        raise(new PresetChangedEvent(userPrefs));
    }
    // @@author
    //@@author A0139046E
    @Override
    public synchronized void addTaskToIndex(Task task, int index) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTaskToIndex(task, index);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    //@@author

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
    //@@author A0139121R
    @Override
    public void updateSortTaskList(HashMap<String, String> dateRange, ArrayList<String> sortByAttribute, String doneStatus, boolean reverse){
        sortList(sortByAttribute, reverse);
        updateSortTaskList(new PredicateExpression(new SortQualifier(dateRange, doneStatus)));
    }
    
    private void updateSortTaskList(Expression expression){
        filteredTasks.setPredicate(expression::satisfies);
    }
    @Override
    public void updateFilteredListToShowUndone() {
        updateFilteredListToShowUndone(new PredicateExpression(new UndoneQualifier()));
    }
    
    private void updateFilteredListToShowUndone(Expression expression){
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowDone() {
        updateFilteredListToShowUndone(new PredicateExpression(new DoneQualifier()));
    }
    
    private void updateFilteredListToShowDone(Expression expression){
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    //========== Inner classes/interfaces used for sorting ====================================================
    
    private void sortList(ArrayList<String> sortByAttribute, boolean reverse){
        addressBook.sortTasks(sortByAttribute, reverse);
        
    }
    //@@author

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
    //@@author A0139121R
    private class SortQualifier implements Qualifier{
        private HashMap<String, String> dateRange;
        private ArrayList<String> sortByAttribute;
        private String doneStatus;
        
        SortQualifier(HashMap<String, String> dateRange, String doneStatus){
            this.dateRange = dateRange;
            this.doneStatus = doneStatus;
        }
        
        /**
         * Tests if task is within the date range if specified, is with the correct DoneFlag status if specified 
         */
        @Override
        public boolean run(ReadOnlyTask task) {
            if(!doneStatus.equalsIgnoreCase("all")){
                if(!doneStatus.equalsIgnoreCase(task.getDoneFlag().toString())){
                    return false;
                }
            }
            
            if(!dateRange.isEmpty()){
                if(!task.isDated()){
                    return false;
                } else {
                    ReadOnlyDatedTask datedTask = (DatedTask) task;
                    LocalDateTime currentTaskDateTime = datedTask.getDateTime().getDateTime();
                    try {
                        LocalDateTime startDateTime = DateParser.parseDate(dateRange.get("start"));
                        if(currentTaskDateTime.isBefore(startDateTime)){
                            return false;
                        }
                    } catch (IllegalValueException e1) {
                        System.out.println("Start date and time given is not a valid string");
                        e1.printStackTrace();
                    }
                    try {
                        LocalDateTime endDateTime = DateParser.parseDate(dateRange.get("end"));
                        if(currentTaskDateTime.isAfter(endDateTime)){
                            return false;
                        }
                    } catch (IllegalValueException e) {
                        System.out.println("End date and time given is not a valid string");
                        e.printStackTrace();
                    }
                }
            }
            
            return true;
        }
        
        
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
            return nameKeyWords.stream()
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
    
    private class UndoneQualifier implements Qualifier {

        UndoneQualifier(){
        }
        /**
         * Tests if task's doneFlag is undone
         */
        @Override
        public boolean run(ReadOnlyTask task) {
            if(task.getDoneFlag().isDone()){
                return false;
            } else {
                return true;
            }
        }

    }
    
    private class DoneQualifier implements Qualifier {

        DoneQualifier(){
        }
        /**
         * Tests if task's doneFlag is done.
         */
        @Override
        public boolean run(ReadOnlyTask task) {
            if(task.getDoneFlag().isDone()){
                return true;
            } else {
                return false;
            }
        }

    }
    //@@author
    // @@author A0140155U
    @Override
    public void saveState(String commandText) {
        states.saveState(new TaskBookState(addressBook, commandText));
    }

    @Override
    public String loadPreviousState() throws StateException {
        return loadState(states.loadPreviousState());
    }

    @Override
    public String loadNextState() throws StateException {
        return loadState(states.loadNextState());
    }
    
    private String loadState(TaskBookState newState) {
        addressBook.resetData(newState.getState());
        indicateAddressBookChanged();
        return newState.getCommand();
    }
    // @@author

}
