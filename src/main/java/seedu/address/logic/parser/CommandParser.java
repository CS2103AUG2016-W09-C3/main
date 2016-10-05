package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import seedu.address.commons.exceptions.IllegalValueException;

public class CommandParser implements ParsedCommand{
    
    private static final String COMMAND_DELIMITER = " ";
    private static final String PARAM_DELIMITER = "/";
    public static final String VALUE_OUT_OF_BOUNDS_MESSAGE = "Value index out of bounds: %1$s";
    public static final String NO_PARAM_MESSAGE = "Could not find param: %1$s";
    
    private ArrayList<String> values = new ArrayList<>();
    private HashMap<String, ArrayList<String>> params = new HashMap<>();
    
    public CommandParser(String command){
        loadFromString(command);
    }
    
    private void loadFromString(String command){
        if(command == null || command.isEmpty()){
            return;
        }
        String[] splitted = command.split(COMMAND_DELIMITER);
        
        int index = 0;
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
                currentParamValue.append(token.substring(paramDelimIndex + 1, token.length()));
            }else{
                currentParamValue.append(COMMAND_DELIMITER);
                currentParamValue.append(token);
            }
        }
        addParam(currentParam, currentParamValue.toString());
    }

    private void addParam(String currentParam, String currentParamValue) {
        if(currentParam != null){
            if(!params.containsKey(currentParam)){
                params.put(currentParam, new ArrayList<>());
            }
            params.get(currentParam).add(currentParamValue);
        }
    }
    
    private boolean isParamToken(String token){
        return token.contains(PARAM_DELIMITER);
    }

    @Override
    public String getParam(String paramName) throws IllegalValueException {
        if(!params.containsKey(paramName) || params.get(paramName).size() == 0){
            throw new IllegalValueException(String.format(NO_PARAM_MESSAGE, paramName));
        }
        return params.get(paramName).get(0);
    }

    @Override
    public String getParamOrDefault(String paramName, String defaultParam)  {
        if(!params.containsKey(paramName) || params.get(paramName).size() == 0){
            return defaultParam;
        }
        return params.get(paramName).get(0);
    }
    
    @Override
    public ArrayList<String> getParamList(String paramName) {
        if(!params.containsKey(paramName) || params.get(paramName).size() == 0){
            return new ArrayList<String>();
        }
        ArrayList<String> readOnlyParamData = new ArrayList<> (params.get(paramName));
        return readOnlyParamData;
    }
    
    @Override
    public String getValue() throws IllegalValueException {
        return getValue(0);
    }

    @Override
    public String getValue(int index) throws IllegalValueException {
        if(index >= values.size()){
            throw new IllegalValueException(String.format(VALUE_OUT_OF_BOUNDS_MESSAGE, index));
        }
        return values.get(index);
    }

    @Override
    public ArrayList<String> getAllValues(){
        ArrayList<String> readOnlyValues = new ArrayList<> (values);
        return readOnlyValues;
    }
    
    @Override
    public ArrayList<String> getAllParams(){
        ArrayList<String> readOnlyParams = new ArrayList<> (params.keySet());
        return readOnlyParams;
    }

    @Override
    public String getValuesAsString() {
        return String.join(" ", values);
    }
}
