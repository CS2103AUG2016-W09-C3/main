// @@author A0140155U
package todoit.taskbook.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import todoit.taskbook.model.CommandPreset;
import todoit.taskbook.model.task.ReadOnlyDatedTask;
import todoit.taskbook.model.task.ReadOnlyTask;

public class PresetCard extends UiPart{

    private static final String FXML = "PresetListCard.fxml";
    private static final TaskStyleMapping styler = TaskPropertyMapping.getInstance();

    @FXML
    private HBox cardPane;
    @FXML
    private Label presetIndex;
    @FXML
    private Label description;
    @FXML
    private Tooltip tooltip;

    private CommandPreset commandPreset;
    private int index;
    
    public PresetCard(){

    }

    public static PresetCard load(CommandPreset commandPreset, int index){
        PresetCard card = new PresetCard();
        card.commandPreset = commandPreset;
        card.index = index;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        description.setText(commandPreset.getDescription());
        presetIndex.setText(index + ". ");
        tooltip.setText(commandPreset.getCommand());
    }
    
    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
