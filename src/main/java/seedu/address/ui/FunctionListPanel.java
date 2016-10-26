package seedu.address.ui;

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
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Panel containing the list of functions.
 */
public class FunctionListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(FunctionListPanel.class);
    private static final String FXML = "FunctionListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> functionListView;

    public FunctionListPanel() {
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

    public static FunctionListPanel load(Stage primaryStage, AnchorPane functionListPlaceholder,
                                       ObservableList<ReadOnlyTask> functionList) {
        FunctionListPanel functionListPanel =
                UiPartLoader.loadUiPart(primaryStage, functionListPlaceholder, new FunctionListPanel());
        functionListPanel.configure(functionList);
        return functionListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> functionList) {
        setConnections(functionList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> functionList) {
        functionListView.setItems(functionList);
        functionListView.setCellFactory(listView -> new FunctionListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        functionListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in function list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            functionListView.scrollTo(index);
            functionListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class FunctionListViewCell extends ListCell<ReadOnlyTask> {

        public FunctionListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask function, boolean empty) {
            super.updateItem(function, empty);

            if (empty || function == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(FunctionCard.load(function, getIndex() + 1).getLayout());
            }
        }
    }

}
