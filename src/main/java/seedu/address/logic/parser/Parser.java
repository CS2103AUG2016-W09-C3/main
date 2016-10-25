package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.model.task.DateParser;
import seedu.address.model.task.DoneFlag;
import seedu.address.model.task.Recurrance;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+) ?(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    /*
    Deprecated
    
    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " (?<isPhonePrivate>p?)p/(?<phone>[^/]+)"
                    + " (?<isEmailPrivate>p?)e/(?<email>[^/]+)"
                    + " (?<isAddressPrivate>p?)a/(?<address>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    */
    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        
        ParsedCommand command = new CommandParser(arguments);
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(command);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            //return new ClearCommand();
            return prepareClear(command);

        case FindCommand.COMMAND_WORD:
            return prepareFind(command);

        case ListCommand.COMMAND_WORD:
            //return new ListCommand();
            return prepareList(command);
            
        case EditCommand.COMMAND_WORD:
        	return prepareEdit(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case DoneCommand.COMMAND_WORD:
            return prepareDone(command);

        case UndoneCommand.COMMAND_WORD:
            return prepareUndone(command);

        case RescheduleCommand.COMMAND_WORD:
        	return prepareReschedule(command);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case FilepathCommand.COMMAND_WORD:
            return new FilepathCommand(arguments);
            
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    //@@author A0139121R
    /**
     * Parses arguments in the context of the list command.
     * 
     * @param command
     * @return
     */
    private Command prepareList(ParsedCommand command) {
        HashMap<String, String> dateRange = new HashMap<String, String>();
        ArrayList<String> sortByAttribute = new ArrayList<String>();
        boolean reverse = false;
        try{
            if(command.hasParams(ListCommand.START_DATE_PARAM)){
                dateRange.put("start", command.getParam(ListCommand.START_DATE_PARAM[0]));
            }
            if(command.hasParams(ListCommand.END_DATE_PARAM)){
                dateRange.put("end", command.getParam(ListCommand.END_DATE_PARAM[0]));
            }
            
            if(command.hasParams(ListCommand.SORT_PARAM)){
                //sortByAttribute = new ArrayList<String>(Arrays.asList(command.getParam("s").split(" ")));
                sortByAttribute = command.getAllParams("s");
            }
            if(command.hasParams(ListCommand.REVERSE_PARAM)){
                reverse = true;
            }
        } catch (IllegalValueException ive){
            return new IncorrectCommand(ive.getMessage());
        }
        
        try {
            return new ListCommand(
                    dateRange,
                    sortByAttribute,
                    command.getParamOrDefault("df", "Not done"),
                    reverse);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    //@@author A0139121R
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(ParsedCommand command){
        if(!command.hasValue() || !command.hasParams(AddCommand.REQUIRED_PARAMS) || command.hasUnnecessaryParams(AddCommand.POSSIBLE_PARAMS)){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            if(command.hasParams(AddCommand.DATED_TASK_PARAMS)){
                return new AddCommand(
                        command.getValuesAsString(),
                        command.getParamOrDefault("d", "-1"),
                        command.getParamOrDefault("l", "-1"),
                        command.getParamOrDefault("de", "-1"),
                        command.getParamOrDefault("r", Recurrance.NO_INTERVAL),
                        command.getParamOrDefault("p", "medium"),
                        command.getParamOrDefault("i", ""),
                        DoneFlag.NOT_DONE,
                        getTagsFromArgs(command.getAllParams("t"))
                );
            } else {
                return new AddCommand(
                        command.getValuesAsString(),
                        command.getParamOrDefault("p", "medium"),
                        command.getParamOrDefault("i", ""),
                        DoneFlag.NOT_DONE,
                        getTagsFromArgs(command.getAllParams("t"))
                );
            }
        } catch (IllegalValueException ive){
            return new IncorrectCommand(ive.getMessage());
        }
    }
    //@@author

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(ArrayList<String> tagArguments) throws IllegalValueException {
        HashSet<String> tagStrings = new HashSet<>();
        for(String tag : tagArguments){
            tagStrings.add(tag);
        }
        return tagStrings;
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }
    
    
    //@@author A0139121R
    /**
     * Checks if clear command has any other users input behind clear command word.
     * 
     * @param command
     * @return clear command
     */
    private Command prepareClear(ParsedCommand command){
        if(command.getAllValues().size() > 0){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
        return new ClearCommand();
    }
    
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
    	ParsedCommand command = new CommandParser(args);
    	if(!command.hasValue() || !command.hasParams(EditCommand.REQUIRED_PARAMS)){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    	try {
    		return new EditCommand(
                        Integer.parseInt(command.getValuesAsString()),
                        command.getParamOrDefault("n", ""),
                        command.getParamOrDefault("d", "-1"),
                        command.getParamOrDefault("l", "-1"),
                        command.getParamOrDefault("r", Recurrance.NO_INTERVAL),
                        command.getParamOrDefault("p", ""),
                        command.getParamOrDefault("i", ""),
                        DoneFlag.NOT_DONE,
                        getTagsFromArgs(command.getAllParams("t"))
                );
        } catch (IllegalValueException ive){
            return new IncorrectCommand(ive.getMessage());
        }
    }
    //@@author
    /**
     * Parses arguments in the context of the reschedule task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareReschedule(ParsedCommand command){
    	if(!command.hasValue() || !command.hasParams(EditCommand.REQUIRED_PARAMS) || !command.hasValueAtIndex(1)){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RescheduleCommand.MESSAGE_USAGE));
        }
    	try {
    		return new RescheduleCommand(
                        Integer.parseInt(command.getValue()),
                        command.getValue(1));
        } catch (IllegalValueException ive){
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }
    // @@author A0140155U
    /**
     * Parses arguments in the context of the done task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDone(ParsedCommand command) {
        try{
            Optional<Integer> index = parseIndex(command.getValue());
            if(!index.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
            }

            return new DoneCommand(index.get());
        }catch(IllegalValueException ex){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }
    
    /**
     * Parses arguments in the context of the undone task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUndone(ParsedCommand command) {
        try{
            Optional<Integer> index = parseIndex(command.getValue());
            if(!index.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
            }

            return new UndoneCommand(index.get());
        }catch(IllegalValueException ex){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }
    // @@author
    
    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    //@@author A0139121R
    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(ParsedCommand command) {
        if(!command.hasValue() || !command.hasParams(FindCommand.REQUIRED_PARAMS) || command.hasUnnecessaryParams(FindCommand.POSSIBLE_PARAMS)){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        try{
            return new FindCommand(new HashSet<String>(command.getAllValues()), new HashSet<String>(command.getAllParams("s")));
        } catch (IllegalValueException ive){
            return new IncorrectCommand(ive.getMessage());
        }
    }
    //@@author
}