package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;

public interface ParsedCommand {

    String getParam(String paramName) throws IllegalValueException;

    String getParamOrDefault(String paramName, String defaultParam) throws IllegalValueException;
    
    String getValue() throws IllegalValueException;
    
    String getValue(int index) throws IllegalValueException;
    
    String getCommandName() throws IllegalValueException;
}
