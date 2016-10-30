//@@author A0139121R
package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Finds and lists all tasks whose name contains any of the argument keywords in the specified attributes.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    
    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {"s"};
    public static final HashSet<String> defaultSearchScope = new HashSet<String>(Arrays.asList("name", "information", "tag"));
    public static final HashSet<String> ALL_POSSIBLE_PARAMS = new HashSet<String>(Arrays.asList("name", "information", "tag"));

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) from the specified attributes and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS] [s/information s/name s/tag]...\n"
            + "Example: " + COMMAND_WORD + " meeting s/name s/tag \n";

    private final Set<String> keywords;
    private HashSet<String> searchScope;

    public FindCommand(Set<String> keywords, HashSet<String> searchScope) throws IllegalValueException{
        if (keywords.isEmpty()){
            throw new IllegalValueException(MESSAGE_USAGE);
        }
        for(String scope : searchScope){
            if(!ALL_POSSIBLE_PARAMS.contains(scope)){
                throw new IllegalValueException("Invalid search scope, use only name, information and date " + MESSAGE_USAGE);
            }
        }
        this.keywords = keywords;
        this.searchScope = searchScope;
        if(this.searchScope.isEmpty()){
            this.searchScope = defaultSearchScope;
        }
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords, searchScope);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean createsNewState() {
        return false;
    }
}
