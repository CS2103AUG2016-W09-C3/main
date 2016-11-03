// @@author A0140155U
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import todoit.taskbook.model.CommandPreset;
import todoit.taskbook.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class PresetCardHandle extends GuiHandle {
    private static final String COMMAND_FIELD_ID = "#command";
    private static final String DESCRIPTION_FIELD_ID = "#description";

    private Node node;

    public PresetCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getCommand() {
        return getTextFromLabel(COMMAND_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public boolean isSamePreset(CommandPreset preset){
        return getDescription().equals(preset.getDescription());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PresetCardHandle) {
            PresetCardHandle handle = (PresetCardHandle) obj;
            return getCommand().equals(handle.getCommand())
                    && getDescription().equals(handle.getDescription());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getCommand() + " " + getDescription();
    }
}
