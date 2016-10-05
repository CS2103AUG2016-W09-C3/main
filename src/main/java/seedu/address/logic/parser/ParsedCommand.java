package seedu.address.logic.parser;

public interface ParsedCommand {

    String getParam(String paramName);

    String getValue();
    
    String getValue(int index);
    
    String getCommandName();
}
