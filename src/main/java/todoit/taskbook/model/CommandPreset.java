//@@author A0140155U
package todoit.taskbook.model;

import java.io.Serializable;
/**
 * Command Preset class.
 * Used to store user's favourite commands.
 * Each preset stores a command and a description.
 */
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
    
    public CommandPreset(CommandPreset commandPreset) {
        this.command = commandPreset.getCommand();
        this.description = commandPreset.getDescription();
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
