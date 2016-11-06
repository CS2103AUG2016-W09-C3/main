// @@author A0140155U
package todoit.taskbook.commons.events.model;

import todoit.taskbook.commons.events.BaseEvent;
import todoit.taskbook.model.UserPrefs;

/** Indicates the TaskBook in the model has changed*/
public class PresetChangedEvent extends BaseEvent {

    public final UserPrefs data;

    public PresetChangedEvent(UserPrefs data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of presets: " + data.getNumPresets();
    }
}
//@@author
