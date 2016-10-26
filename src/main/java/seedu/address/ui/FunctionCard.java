package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.logic.commands.CommandPreset;
import seedu.address.model.task.ReadOnlyDatedTask;
import seedu.address.model.task.ReadOnlyTask;

public class FunctionCard extends UiPart{

    private static final String FXML = "FunctionListCard.fxml";
    private static final TaskStyleMapping styler = TaskPropertyMapping.getInstance();
    
    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label command;

    private CommandPreset commandPreset;

    public FunctionCard(){

    }

    public static FunctionCard load(CommandPreset commandPreset){
        FunctionCard card = new FunctionCard();
        card.commandPreset = commandPreset;
        return UiPartLoader.loadUiPart(card);
    }

    // @@author A0140155U
    @FXML
    public void initialize() {
        command.setText(commandPreset.getCommand());
        description.setText(commandPreset.getDescription());
    }
    
    // @@author
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
