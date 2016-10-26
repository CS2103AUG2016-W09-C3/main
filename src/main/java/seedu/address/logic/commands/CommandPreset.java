package seedu.address.logic.commands;

import java.io.Serializable;

public class CommandPreset implements Serializable{
    private final String command;
    private final String description;
    
    public CommandPreset(){
        this.command = "";
        this.description = "";
    }
    
    public CommandPreset(String command, String description){
        this.command = command;
        this.description = description;
    }
    
    public String getCommand() {
        return command;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString(){
        return command;
    }
}
