package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Iterator;

import seedu.address.commons.exceptions.IllegalValueException;

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
     * Retrieves a list of params corresponding to the param name.
     * If the param name has no values, returns an empty list.
     */
    ArrayList<String> getAllParams();
    
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
     * Get all params.
     */
    ArrayList<String> getParamList(String paramName);

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

    boolean hasUnnecessaryParams(String[] possibleParams);

}
