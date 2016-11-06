//@@author A0139121R
package todoit.taskbook.logic.commands;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.model.tag.Tag;
import todoit.taskbook.model.tag.UniqueTagList;
import todoit.taskbook.model.task.*;

/**
 * Adds a tasks to the task book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String[] REQUIRED_PARAMS = {};
    public static final String[] POSSIBLE_PARAMS = {"d", "l", "de", "r", "p", "i", "t"};
    
    public static final String[] DATED_TASK_PARAMS = {"d"};
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task book. "
            + "Parameters: NAME [d/DATE,TIME l/LENGTH de/END_DATE,END_TIME] [r/RECUR] [p/PRIORITY] [i/INFORMATION] [t/TAG]...\n"
            + "Parameters should not contain '/'s."
            + "Parameters should not contain both length and end date\n"
            + "Example: " + COMMAND_WORD
            + " Meet John d/2pm next thurs l/2h r/1d p/medium i/Meeting for lunch";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";
    public static final String MESSAGE_LENGTH_ENDDATE_CONFLICT = "Length and end datetime cannot be both filled.";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values for adding 
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String datetime, String length, String endDatetime, String recurring, 
                      String priority, String information, String doneFlag, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        String newLength = setLength(length, datetime, endDatetime);
        this.toAdd = (Task) new DatedTask(
                new Name(name),
                new DateTime(datetime),
                new Length(newLength),
                new Recurrence(recurring),
                new Priority(priority),
                new Information(information),
                new DoneFlag(doneFlag),
                new UniqueTagList(tagSet)
        );
    }
    
    private String setLength(String length, String datetime, String endDatetime) throws IllegalValueException{
        if(length.equals("-1")){
            if(endDatetime.equals("-1")){
                return Length.NO_INTERVAL;
            } else {
                LocalDateTime startDate = DateParser.parseDate(datetime);
                LocalDateTime endDate = DateParser.parseDate(endDatetime);
                long hourDifference = ChronoUnit.HOURS.between(startDate, endDate);
                String finalTimeDifference = getDifferenceString(hourDifference);
                return finalTimeDifference;
            }
        } else {
            if(!endDatetime.equals("-1")){
                throw new IllegalValueException(MESSAGE_LENGTH_ENDDATE_CONFLICT);
            }
            return length;
        }
    }
    
    private String getDifferenceString(long hourDifference){
        if(hourDifference >= 24){
            return Long.toString(TimeUnit.HOURS.toDays(hourDifference)) + "d";
        } else {
            return hourDifference + "h";
        }
    }

    public AddCommand(String name, String priority, String information, String doneFlag, Set<String> tagsFromArgs) 
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagsFromArgs) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Priority(priority),
                new Information(information),
                new DoneFlag(doneFlag),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean createsNewState() {
        return true;
    }

}
