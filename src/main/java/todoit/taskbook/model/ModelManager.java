package todoit.taskbook.model;

import javafx.collections.transformation.FilteredList;
import todoit.taskbook.commons.core.ComponentManager;
import todoit.taskbook.commons.core.LogsCenter;
import todoit.taskbook.commons.core.UnmodifiableObservableList;
import todoit.taskbook.commons.events.model.FilePathChangedEvent;
import todoit.taskbook.commons.events.model.PresetChangedEvent;
import todoit.taskbook.commons.events.model.TaskBookChangedEvent;
import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.commons.exceptions.StateException;
import todoit.taskbook.commons.util.StringUtil;
import todoit.taskbook.model.task.CustomTaskComparator;
import todoit.taskbook.model.task.DateParser;
import todoit.taskbook.model.task.DatedTask;
import todoit.taskbook.model.task.DoneFlag;
import todoit.taskbook.model.task.ReadOnlyDatedTask;
import todoit.taskbook.model.task.ReadOnlyTask;
import todoit.taskbook.model.task.Task;
import todoit.taskbook.model.task.UniqueTaskList;
import todoit.taskbook.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Represents the in-memory model of the task book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
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

        logger.fine("Initializing with task book: " + src + " and user prefs " + userPrefs);

        taskBook = new TaskBook(src);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
        // @@author A0140155U
        states = new StatesManager(new TaskBookState(taskBook));
        this.userPrefs = userPrefs;
        // @@author
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        //@@author A0139947L
        taskBook.updateRecurringTasks();
        //@@author
        filteredTasks = new FilteredList<>(taskBook.getTasks());
        // @@author A0140155U
        states = new StatesManager(new TaskBookState(taskBook));
        this.userPrefs = userPrefs;
        // @@author
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
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
        taskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskBookChanged();
    }

    // @@author A0140155U
    @Override
    public void addPreset(CommandPreset commandPreset) {
        userPrefs.addPreset(commandPreset);
        raise(new PresetChangedEvent(userPrefs));
    }
    
    @Override
    public String removePreset(int index) throws IllegalValueException {
        String removedCommandDesc = userPrefs.removePreset(index);
        raise(new PresetChangedEvent(userPrefs));
        return removedCommandDesc;
    }
    // @@author
    //@@author A0139046E
    @Override
    public synchronized void addTaskToIndex(Task task, int index) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTaskToIndex(task, index);
        updateFilteredListToShowAll();
        indicateTaskBookChanged();
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
    //@@author A0139121R
    @Override
    public void updateFilteredTaskList(Set<String> keywords, HashSet<String> searchScope){
        updateFilteredTaskList(new PredicateExpression(new FindQualifier(keywords, searchScope)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
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
        taskBook.sortTasks(sortByAttribute, reverse);
        
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
         * Tests if task is within the date range if specified, and with the correct DoneFlag status if specified 
         */
        @Override
        public boolean run(ReadOnlyTask task) {
            if(!checkDoneFlagSame(task)){
                return false;
            }
            
            if(!dateRange.isEmpty()){
                if(!checkWithinDateRange(task)){
                    return false;
                }
            }
            
            return true;
        }
        private boolean checkWithinDateRange(ReadOnlyTask task){
            if(!task.isDated()){
                return false;
            } else {
                ReadOnlyDatedTask datedTask = (DatedTask) task;
                LocalDateTime currentTaskDateTime = datedTask.getDateTime().getDateTime();
                if(!checkAfterStartDate(datedTask, currentTaskDateTime)){
                    return false;
                }
                if(!checkBeforeEndDate(datedTask, currentTaskDateTime)){
                    return false;
                }
            }
            return true;
        }
        
        private boolean checkAfterStartDate(ReadOnlyDatedTask task, LocalDateTime currentTaskDateTime){
            try {
                LocalDateTime startDateTime = DateParser.parseDate(dateRange.get("start"));
                if(currentTaskDateTime.isBefore(startDateTime)){
                    return false;
                }
            } catch (IllegalValueException e1) {
                System.out.println("Start date and time given is not a valid string");
                e1.printStackTrace();
                return false;
            }
            return true;
        }
        
        private boolean checkBeforeEndDate(ReadOnlyDatedTask task, LocalDateTime currentTaskDateTime){
            try{
                LocalDateTime endDateTime = DateParser.parseDate(dateRange.get("end"));
                if(currentTaskDateTime.isAfter(endDateTime)){
                    return false;
                } 
            } catch (IllegalValueException e2){
                System.out.println("End date and time given is not a valid string");
                e2.printStackTrace();
                return false;
            }
            return true;
        }
        
        private boolean checkDoneFlagSame(ReadOnlyTask task){
            if(!doneStatus.equalsIgnoreCase("all")){
                if(!doneStatus.equalsIgnoreCase(task.getDoneFlag().toString())){
                    return false;
                }
            }
            return true;
        }
    }

    private class FindQualifier implements Qualifier {
        private Set<String> findKeyWords;
        private HashSet<String> searchScope;

        FindQualifier(Set<String> findKeyWords, HashSet<String> searchScope) {
            this.findKeyWords = findKeyWords;
            this.searchScope = searchScope;
        }
        /**
         * Tests if task contains any of the keywords in findKeyWords in the possible specified searchScope of "name" "information" 
         * and "tag".
         */
        @Override
        public boolean run(ReadOnlyTask task) {
            return findKeyWords.stream()
                    .filter(keyword -> (this.searchScope.contains("name") && StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                            || (this.searchScope.contains("information") && StringUtil.containsIgnoreCase(task.getInformation().fullInformation, keyword))
                            || (this.searchScope.contains("tag") && task.getTags().containsStringAsTag(keyword))
                            )
                    .findAny()//finds first one
                    .isPresent();//check if null
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", findKeyWords);
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
        states.saveState(new TaskBookState(taskBook, commandText));
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
        taskBook.resetData(newState.getState());
        indicateTaskBookChanged();
        return newState.getCommand();
    }
    // @@author


}
