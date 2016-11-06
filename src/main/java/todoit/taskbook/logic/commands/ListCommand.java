//@@author A0139121R
package todoit.taskbook.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import todoit.taskbook.commons.exceptions.IllegalValueException;

/**
 * Lists all tasks in the task list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks according to given input.";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks with "
            + "the specified done statues, within the specified period of time and sorted in order based on attributes specified\n"
            + "Parameters: [df/[done/not done/all]] [ds/STARTDATE] [de/ENDDATE] [s/name s/date s/priority] [rev/]...\n"
            + "Example: " + COMMAND_WORD + " df/not done ds/10-31-2016 de/11-20-2016 s/priority s/name\n";
    
    public static final String SORT_MESSAGE_USAGE = "Invalid sort attribute, use only name, priority, date " + MESSAGE_USAGE;

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {"ds", "de", "s", "df", "rev"};
    public static final String[] START_DATE_PARAM = {"ds"};
    public static final String[] END_DATE_PARAM = {"de"};
    public static final String[] START_AND_END_DATE_PARAM = {"ds", "de"};
    public static final String[] SORT_PARAM = {"s"};
    public static final String[] REVERSE_PARAM = {"rev"};
    public static final HashSet<String> ALL_SORT_ATTRIBUTES = new HashSet<String>(Arrays.asList("name", "priority", "date"));
    
    private HashMap<String, String> dateRange;
    private ArrayList<String> sortByAttribute;
    private String doneStatus;
    private boolean reverse;
    
    public ListCommand() {}
    
    /**
     * ListCommand constructor
     * @param dateRange To list tasks that falls within this dateRange.
     * @param sortByAttribute Attributes to sort tasks by, will be passed to custom task comparator.
     * @param doneStatus To only list tasks with this doneStatus
     * @param reverse If specified, reverses the order of the final sorted list.
     * @throws IllegalValueException
     */
    public ListCommand(HashMap<String, String> dateRange, ArrayList<String> sortByAttribute, String doneStatus, boolean reverse) 
            throws IllegalValueException {
        assert dateRange != null : "dateRange given is a null object";
        assert sortByAttribute != null : "sortByAttribute given is a null object";
        for(String sortAttribute : sortByAttribute){
            if(!ALL_SORT_ATTRIBUTES.contains(sortAttribute)){
                throw new IllegalValueException(SORT_MESSAGE_USAGE);
            }
        }
        this.dateRange = dateRange;
        this.sortByAttribute = sortByAttribute;
        this.doneStatus = doneStatus;
        this.reverse = reverse;
    }

    @Override
    public CommandResult execute() {
        model.updateSortTaskList(dateRange, sortByAttribute, doneStatus, reverse);
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    @Override
    public boolean createsNewState() {
        return false;
    }
}
