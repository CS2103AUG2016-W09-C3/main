package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Iterator;

import seedu.address.commons.exceptions.IllegalValueException;

public interface ParsedCommand {

    String getParam(String paramName) throws IllegalValueException;

    String getParamOrDefault(String paramName, String defaultParam);

    ArrayList<String> getAllParams();
    
    String getValue() throws IllegalValueException;
    
    String getValue(int index) throws IllegalValueException;

    ArrayList<String> getAllValues();
    
    String getCommandName();

    ArrayList<String> getParamList(String paramName) throws IllegalValueException;

}
