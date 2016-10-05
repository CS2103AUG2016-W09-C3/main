package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Iterator;

import seedu.address.commons.exceptions.IllegalValueException;

public interface ParsedCommand {

    String getParam(String paramName) throws IllegalValueException;

    String getParamOrDefault(String paramName, String defaultParam);

    ArrayList<String> getAllParams();
    
    boolean hasParams(String[] params);

    boolean hasValue();
    
    String getValue() throws IllegalValueException;
    
    String getValue(int index) throws IllegalValueException;

    String getValuesAsString();
    
    ArrayList<String> getAllValues();

    ArrayList<String> getParamList(String paramName);

}
