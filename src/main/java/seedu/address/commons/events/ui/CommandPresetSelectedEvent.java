// @@author A0140155U
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.CommandPreset;

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
