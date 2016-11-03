// @@author A0140155U
package todoit.taskbook.commons.events.ui;

import todoit.taskbook.commons.events.BaseEvent;
import todoit.taskbook.model.CommandPreset;

/**
 * Indicates a command preset card clicked
 */
public class CommandPresetSelectedEvent extends BaseEvent {

    private final CommandPreset command;
    
    public CommandPresetSelectedEvent(CommandPreset command) {
        this.command = command;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getCommand() {
        return command.getCommand();
    }
}
