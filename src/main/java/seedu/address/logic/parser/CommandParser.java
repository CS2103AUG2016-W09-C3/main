package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;

public class CommandParser {
    
    private static final String COMMAND_DELIMITER = " ";
    private static final String PARAM_DELIMITER = "/";
    
    
    private String baseCommand = null;
    private String commandName = null;
    private ArrayList<String> values = new ArrayList<>();
    private HashMap<String, String> params = new HashMap<>();
    
    public CommandParser(String command) throws IllegalValueException{
        baseCommand = command;
        loadFromString(command);
    }
    
    private void loadFromString(String command) throws IllegalValueException{
        String[] splitted = command.split(COMMAND_DELIMITER);
        
        int index = 0;
        if(index < splitted.length){
            commandName = splitted[index];
        }else{
            return;
        }
        index++;
        
        index = loadValues(splitted, index);
        loadParams(splitted, index);
        
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
}
