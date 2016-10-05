package seedu.address.logic.parser;

import java.util.HashMap;

public class ParsedCommand {
    
    String baseCommand;
    String commandName;
    String value;
    HashMap<String, String> params = new HashMap<String, String>();
    
    public ParsedCommand(String command){
        baseCommand = command;
    }
    
}
