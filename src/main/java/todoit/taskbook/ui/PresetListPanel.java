// @@author A0140155U
package todoit.taskbook.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import todoit.taskbook.commons.core.LogsCenter;
import todoit.taskbook.commons.events.ui.CommandPresetSelectedEvent;
import todoit.taskbook.commons.events.ui.TaskPanelSelectionChangedEvent;
import todoit.taskbook.model.CommandPreset;
import todoit.taskbook.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of presets.
 */
public class PresetListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(PresetListPanel.class);
    private static final String FXML = "PresetListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<CommandPreset> presetListView;

    public PresetListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static PresetListPanel load(Stage primaryStage, AnchorPane presetListPlaceholder,
                                       ObservableList<CommandPreset> presetList) {
        PresetListPanel presetListPanel =
                UiPartLoader.loadUiPart(primaryStage, presetListPlaceholder, new PresetListPanel());
        presetListPanel.configure(presetList);
        return presetListPanel;
    }

    private void configure(ObservableList<CommandPreset> presetList) {
        setConnections(presetList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<CommandPreset> presetList) {
        presetListView.setItems(presetList);
        presetListView.setCellFactory(listView -> new PresetListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        presetListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in preset list panel changed to : '" + newValue + "'");
                raise(new CommandPresetSelectedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            presetListView.scrollTo(index);
            presetListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class PresetListViewCell extends ListCell<CommandPreset> {

        public PresetListViewCell() {
        }

        @Override
        protected void updateItem(CommandPreset commandPreset, boolean empty) {
            super.updateItem(commandPreset, empty);

            if (empty || commandPreset == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(PresetCard.load(commandPreset, getIndex() + 1).getLayout());
            }
        }
    }

}
