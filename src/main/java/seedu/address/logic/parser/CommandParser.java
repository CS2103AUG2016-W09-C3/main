package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;

public class CommandParser implements ParsedCommand{
    
    private static final String COMMAND_DELIMITER = " ";
    private static final String PARAM_DELIMITER = "/";
    public static final String VALUE_OUT_OF_BOUNDS_MESSAGE = "Value index out of bounds: %1$s";
    public static final String NO_PARAM_MESSAGE = "Could not find param: %1$s";
    public static final String NO_COMMAND_NAME_MESSAGE = "No command name available";
    
    private String commandName = null;
    private ArrayList<String> values = new ArrayList<>();
    private HashMap<String, String> params = new HashMap<>();
    
    public CommandParser(String command) throws IllegalValueException{
        loadFromString(command);
    }
    
    private void loadFromString(String command) throws IllegalValueException{
        String[] splitted = command.split(COMMAND_DELIMITER);
        
        int index = 0;
        index = loadCommandName(splitted, index);
        index = loadValues(splitted, index);
        loadParams(splitted, index);
        
    }

    private int loadCommandName(String[] splitted, int index) {
        if(index < splitted.length){
            commandName = splitted[index];
        }
        index++;
        return index;
    }

    private int loadValues(String[] splitted, int index) {
        while(index < splitted.length && !isParamToken(splitted[index])){
            values.add(splitted[index]);
            index++;
        }
        return index;
    }

    private void loadParams(String[] splitted, int index) {
        String currentParam = null;
        StringBuilder currentParamValue = new StringBuilder();
        for(int i = index; i < splitted.length; i++){
            String token = splitted[i];
            if(isParamToken(token)){
                addParam(currentParam, currentParamValue.toString());
                
                int paramDelimIndex = token.indexOf(PARAM_DELIMITER);
                currentParam = token.substring(0, paramDelimIndex);
                currentParamValue = new StringBuilder();
                currentParamValue.append(token.substring(paramDelimIndex, token.length()));
            }else{
                currentParamValue.append(COMMAND_DELIMITER);
                currentParamValue.append(token);
            }
        }
        addParam(currentParam, currentParamValue.toString());
    }

    private void addParam(String currentParam, String currentParamValue) {
        if(currentParam != null){
            params.put(currentParam, currentParamValue);
        }
    }
    
    private boolean isParamToken(String token){
        return token.contains(PARAM_DELIMITER);
    }

    @Override
    public String getParam(String paramName) throws IllegalValueException {
        if(!params.containsKey(paramName)){
            throw new IllegalValueException(String.format(VALUE_OUT_OF_BOUNDS_MESSAGE, paramName));
        }
        return params.get(paramName);
    }

    @Override
    public String getParamOrDefault(String paramName, String defaultParam)  {
        if(!params.containsKey(paramName)){
            return defaultParam;
        }
        return params.get(paramName);
    }
    
    @Override
    public String getValue() throws IllegalValueException {
        if(values.isEmpty()){
            throw new IllegalValueException(String.format(VALUE_OUT_OF_BOUNDS_MESSAGE, 0));
        }
        return values.get(0);
    }

    @Override
    public String getValue(int index) throws IllegalValueException {
        if(index >= values.size()){
            throw new IllegalValueException(String.format(VALUE_OUT_OF_BOUNDS_MESSAGE, index));
        }
        return values.get(index);
    }

    @Override
    public String getCommandName() throws IllegalValueException {
        if(commandName == null){
            throw new IllegalValueException(NO_COMMAND_NAME_MESSAGE);
        }
        return commandName;
    }
}
