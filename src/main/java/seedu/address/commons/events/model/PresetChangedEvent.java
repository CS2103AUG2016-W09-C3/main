// @@author A0140155U
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.UserPrefs;

/** Indicates the TaskBook in the model has changed*/
public class PresetChangedEvent extends BaseEvent {

    public final UserPrefs data;

    public PresetChangedEvent(UserPrefs data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of presets: " + data.commandPresets.size();
    }
}
//@@author
