// @@author A0140155U
package todoit.taskbook.logic.parser;

import java.util.ArrayList;
import java.util.Iterator;

import todoit.taskbook.commons.exceptions.IllegalValueException;

/*
 * Interface for a parsed command
 */
public interface ParsedCommand {
    
    /**
     * Retrieves the first param from the list corresponding to the param name.
     * Throws an error if the param name has no values.
     */
    String getParam(String paramName) throws IllegalValueException;

    /**
     * Retrieves the first param from the list corresponding to the param name.
     * If the param name has no values, return the default value.
     */
    String getParamOrDefault(String paramName, String defaultParam);

    /**
     * Get all params.
     */
    ArrayList<String> getParamList();
    
    /**
     * Retrieves the very first value in the list.
     * Use when you expect only one value in the command.
     */
    String getValue() throws IllegalValueException;

    /**
     * Retrieves the nth value in the list.
     */
    String getValue(int index) throws IllegalValueException;

    /**
     * Get all values.
     */
    ArrayList<String> getAllValues();

    /**
     * Retrieves a list of params corresponding to the param name.
     * If the param name has no values, returns an empty list.
     */
    ArrayList<String> getAllParams(String paramName);

    /**
     * Retrieves list of values joined with spaces.
     */
    String getValuesAsString();


    /**
     * Check if command contains all the params in the array.
     * Useful for checking if the command is valid.
     */
    boolean hasParams(String[] params);

    /**
     * Check if command has at least one value.
     * Useful for checking if the command is valid.
     */
    boolean hasValue();
    
    //@@author A0139046E
    /**
     * Check if command has value at the particular index
     * Useful for checking if the command is valid.
     */
    boolean hasValueAtIndex(int index);
    //@@author
    // @@author A0140155U
    /**
     * Check if command has any unnecessary params, given a list of valid params
     */
    boolean hasUnnecessaryParams(String[] possibleParams);
    
    /** Returns entire command. */
    String getCommand();
}
//@@author