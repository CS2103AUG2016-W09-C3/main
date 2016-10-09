package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String INFORMATION_FIELD_ID = "#information";
    private static final String DONEFLAG_FIELD_ID = "#done";

    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getInformation() {
        return getTextFromLabel(INFORMATION_FIELD_ID);
    }

    public String getDoneFlag() {
        return getTextFromLabel(DONEFLAG_FIELD_ID);
    }

    public boolean isSamePerson(ReadOnlyTask task){
        return getFullName().equals(task.getName().fullName) && getInformation().equals(task.getInformation().toString())
                && getDoneFlag().equals(task.getDoneFlag().toString()) && getPriority().equals(task.getPriority().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getPriority().equals(handle.getPriority()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getPriority();
    }
}
