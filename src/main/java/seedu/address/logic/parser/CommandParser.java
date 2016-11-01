// @@author A0140155U
package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Parses a command into values and parameters.
 */
public class CommandParser implements ParsedCommand{
    
    private static final String COMMAND_DELIMITER = " ";
    private static final String PARAM_DELIMITER = "/";
    public static final String VALUE_OUT_OF_BOUNDS_MESSAGE = "Value index out of bounds: %1$s";
    public static final String NO_PARAM_MESSAGE = "Could not find param: %1$s";
    
    /*
     * A command consists of values, followed by parameters.
     * A value is a string that does not contain a /.
     * A parameter consists of a param name and a param value, seperated by a / e.g name/value.
     * 
     * A param can have multiple values, when multiple params have the same name, all values
     * are group in one param.
     */
    
    // List of values
    private ArrayList<String> values = new ArrayList<>();
    // Param name -> list of param values
    private HashMap<String, ArrayList<String>> params = new HashMap<>();
    private final String command;
    
    public CommandParser(String command){
        this.command = command;
        loadFromString(command);
    }
    
    /**
     * Loads values and params from string.
     */
    private void loadFromString(String command){
        if(command == null || command.isEmpty()){
            return;
        }
        String[] splitted = command.split(COMMAND_DELIMITER);
        
        // Find all values
        int firstParamIndex = loadValues(splitted);
        // Find all params
        loadParams(splitted, firstParamIndex);
        
    }

    /**
     * Loads values from command.
     * Returns the index of the first param in the command.
     * 
     * i.e.
     * ["value1", "value2", "value3", "p1/param1", "p1/param1a", "p2/param2"]
     * Loads values = ["value1", "value2", "value3"] 
     * return 3
     */
    private int loadValues(String[] splitted) {
        // Load string tokens until we encounter a param token
        int index = 0;
        while(index < splitted.length && !isParamToken(splitted[index])){
            values.add(splitted[index]);
            index++;
        }
        return index;
    }

    /**
     * Loads params from command.
     * 
     * i.e.
     * ["value1", "value2", "value3", "p1/param1", "p1/param1a", "p2/param2"], firstParamIndex = 3
     * Loads params = {p1 : ["param1", "param1a"], p2 : ["param2"]} 
     */
    private void loadParams(String[] splitted, int firstParamIndex) {
        // This stores the param name
        String currentParam = null;
        // This stores the param value
        StringBuilder currentParamValue = new StringBuilder();
        for(int i = firstParamIndex; i < splitted.length; i++){
            String token = splitted[i];
            if(isParamToken(token)){
                // New param, flush the param value, add the old param and start over
                addParam(currentParam, currentParamValue.toString());
                int paramDelimIndex = token.indexOf(PARAM_DELIMITER);
                currentParam = token.substring(0, paramDelimIndex).toLowerCase();
                currentParamValue = new StringBuilder();
                currentParamValue.append(token.substring(paramDelimIndex + 1, token.length()));
            }else{
                // Not a param, add the token on to the value
                currentParamValue.append(COMMAND_DELIMITER);
                currentParamValue.append(token);
            }
        }
        addParam(currentParam, currentParamValue.toString());
    }


    /**
     * Adds a param to the param list.
     */
    private void addParam(String currentParam, String currentParamValue) {
        if(currentParam != null){
            if(!params.containsKey(currentParam)){
                // If list doesn't exist, create one
                params.put(currentParam, new ArrayList<>());
            }
            params.get(currentParam).add(currentParamValue);
        }
    }
    
    /**
     * Checks if a string token is a param.
     */
    private boolean isParamToken(String token){
        return token.contains(PARAM_DELIMITER);
    }

    /**
     * Retrieves the first param from the list corresponding to the param name.
     * Throws an error if the param name has no values.
     */
    @Override
    public String getParam(String paramName) throws IllegalValueException {
        if(!params.containsKey(paramName) || params.get(paramName).size() == 0){
            throw new IllegalValueException(String.format(NO_PARAM_MESSAGE, paramName));
        }
        return params.get(paramName).get(0);
    }
    
    /**
     * Retrieves the first param from the list corresponding to the param name.
     * If the param name has no values, return the default value.
     */
    @Override
    public String getParamOrDefault(String paramName, String defaultParam)  {
        if(!params.containsKey(paramName) || params.get(paramName).size() == 0){
            return defaultParam;
        }
        return params.get(paramName).get(0);
    }
    
    /**
     * Retrieves a list of params corresponding to the param name.
     * If the param name has no values, returns an empty list.
     */
    @Override
    public ArrayList<String> getAllParams(String paramName) {
        if(!params.containsKey(paramName) || params.get(paramName).size() == 0){
            return new ArrayList<String>();
        }
        ArrayList<String> readOnlyParamData = new ArrayList<> (params.get(paramName));
        return readOnlyParamData;
    }
    
    /**
     * Retrieves the very first value in the list.
     * Use when you expect only one value in the command.
     */
    @Override
    public String getValue() throws IllegalValueException {
        return getValue(0);
    }
    
    /**
     * Retrieves the nth value in the list.
     */
    @Override
    public String getValue(int index) throws IllegalValueException {
        if(index >= values.size()){
            throw new IllegalValueException(String.format(VALUE_OUT_OF_BOUNDS_MESSAGE, index));
        }
        return values.get(index);
    }

    /**
     * Get all values as an arraylist.
     */
    @Override
    public ArrayList<String> getAllValues(){
        ArrayList<String> readOnlyValues = new ArrayList<> (values);
        return readOnlyValues;
    }
    

    /**
     * Get all params as an arraylist.
     */
    @Override
    public ArrayList<String> getParamList(){
        ArrayList<String> readOnlyParams = new ArrayList<> (params.keySet());
        return readOnlyParams;
    }

    /**
     * Retrieves list of values joined with spaces.
     */
    @Override
    public String getValuesAsString() {
        return String.join(" ", values);
    }

    /**
     * Check if command contains all the params in the array.
     * Useful for checking if the command is valid.
     */
    @Override
    public boolean hasParams(String[] requiredParams) {
        for(String param : requiredParams){
            if(!params.containsKey(param)){
                return false;
            }
        }
        return true;
    }

    /**
     * Check if command has at least one value.
     * Useful for checking if the command is valid.
     */
    @Override
    public boolean hasValue() {
        return values.size() > 0;
    }
    
    //@@author A0139046E
    /**
     * Check if command has value at the particular index
     * Useful for checking if the command is valid.
     */
    @Override
    public boolean hasValueAtIndex(int index){
    	return values.size() > index;
    }
    //@@author
    // @@author A0140155U
    @Override
    public boolean hasUnnecessaryParams(String[] possibleParams) {
        for(String param : params.keySet()){
            if(!matchingParam(param.toLowerCase(), possibleParams)){
                return true;
            }
        }
        return false;
    }
    
    private boolean matchingParam(String param, String[] possibleParams){
        for(String possibleParam : possibleParams){
            if(param.toLowerCase().matches(possibleParam)){
                return true;
            }
        }
        return false;
    }
    
    public String getCommand(){
        return command;
    }
}
//@@author
