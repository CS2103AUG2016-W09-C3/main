// @@author A0140155U
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.logic.commands.CommandPreset;
import seedu.address.model.task.ReadOnlyDatedTask;
import seedu.address.model.task.ReadOnlyTask;

public class PresetCard extends UiPart{

    private static final String FXML = "PresetListCard.fxml";
    private static final TaskStyleMapping styler = TaskPropertyMapping.getInstance();
    
    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label command;

    private CommandPreset commandPreset;

    public PresetCard(){

    }

    public static PresetCard load(CommandPreset commandPreset){
        PresetCard card = new PresetCard();
        card.commandPreset = commandPreset;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        command.setText(commandPreset.getCommand());
        description.setText(commandPreset.getDescription());
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
