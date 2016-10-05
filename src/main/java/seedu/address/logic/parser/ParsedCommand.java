package seedu.address.logic.parser;

import java.util.Iterator;

import seedu.address.commons.exceptions.IllegalValueException;

public interface ParsedCommand {

    String getParam(String paramName) throws IllegalValueException;

    String getParamOrDefault(String paramName, String defaultParam);

    Iterator<String> getAllParams();
    
    String getValue() throws IllegalValueException;
    
    String getValue(int index) throws IllegalValueException;

    Iterator<String> getAllValues();
    
    String getCommandName();

    Iterator<String> getParamList(String paramName) throws IllegalValueException;

}
