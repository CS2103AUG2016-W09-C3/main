package todoit.taskbook.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import todoit.taskbook.model.task.ReadOnlyDatedTask;
import todoit.taskbook.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    private static final TaskStyleMapping styler = TaskPropertyMapping.getInstance();
    
    @FXML
    private HBox taskPane;

    @FXML
    private VBox idLight;
    @FXML
    private VBox taskContainer;
    
    @FXML
    private Label name;
    @FXML
    private Label done;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label information;
    @FXML
    private Label datetime;
    @FXML
    private Label recurrence;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    // @@author A0140155U
    @FXML
    public void initialize() {
        initTexts();
        style();
    }

    private void initTexts() {
        name.setText(task.getName().fullName);
        id.setText(Integer.toString(displayedIndex));
        setOrDelete(priority, "Priority: ", task.getPriority().getDisplayedAlias());
        setOrDelete(information, "Info: ", task.getInformation().toString());
        setOrDelete(done, "", task.getDoneFlag().toString());
        if(task.isDated()){
            ReadOnlyDatedTask datedTask = (ReadOnlyDatedTask) task;
            setOrDelete(datetime, "Date: ",
                    datedTask.getDateTime().toString() + (datedTask.hasValidLength() ? " - " + datedTask.getDateTimeEnd().toString() : ""));
            setOrDelete(recurrence, "Repeat every ", datedTask.getRecurrence().toString());
        }else{
            deleteLabel(datetime);
            deleteLabel(recurrence);
        }
        tags.setText(task.tagsString());
    }
    
    private void setOrDelete(Label label, String prefix, String value){
        if(value.equals("")){
            deleteLabel(label);
        }else{
            label.setText(prefix + value);
        }
    }
    
    private void deleteLabel(Label label){
        taskContainer.getChildren().remove(label);
    }
    
    private void style(){
        StringBuilder styleString = new StringBuilder();
        // Style based on property
        styleString.append(styler.getLightPriorityColour(task.getPriority().toString()));
        // Style based on done
        styleString.append(styler.getLightDoneColour(task.getDoneFlag().toString()));
        idLight.setStyle(styleString.toString());
        
        // Style based on done
        //styleString = new StringBuilder();
        //styleString.append(styler.getCardDoneColour(task.getDoneFlag().toString()));
        //taskPane.setStyle(styleString.toString());
        
    }
    
    // @@author
    public HBox getLayout() {
        return taskPane;
    }

    @Override
    public void setNode(Node node) {
        taskPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
