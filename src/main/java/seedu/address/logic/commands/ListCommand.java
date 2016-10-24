//@@author A0139121R
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks according to given input.";
    
    //public static final String MESSAGE_SORT_SUCCESS = "";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {"ds", "de", "s", "df", "rev"};
    public static final String[] START_DATE_PARAM = {"ds"};
    public static final String[] END_DATE_PARAM = {"de"};
    public static final String[] START_AND_END_DATE_PARAM = {"ds", "de"};
    public static final String[] SORT_PARAM = {"s"};
    public static final String[] REVERSE_PARAM = {"rev"};
    
    private HashMap<String, String> dateRange;
    private ArrayList<String> sortByAttribute;
    private String doneStatus;
    private boolean reverse;
    
    public ListCommand() {}
    
    public ListCommand(HashMap<String, String> dateRange, ArrayList<String> sortByAttribute, String doneStatus, boolean reverse) {
        assert dateRange != null : "dateRange given is a null object";
        assert sortByAttribute != null : "sortByAttribute given is a null object";
        this.dateRange = dateRange;
        this.sortByAttribute = sortByAttribute;
        this.doneStatus = doneStatus;
        this.reverse = reverse;
    }

    @Override
    public CommandResult execute() {
        if(dateRange.isEmpty() && sortByAttribute.isEmpty() && doneStatus.equalsIgnoreCase("Not done") && !reverse){
            model.updateFilteredListToShowUndone();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.updateSortTaskList(dateRange, sortByAttribute, doneStatus, reverse);
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
    
    @Override
    public boolean createsNewState() {
        return false;
    }
}
