// @@author A0140155U
package todoit.taskbook.commons.events.ui;

import todoit.taskbook.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of presets
 */
public class JumpToPresetRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToPresetRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
