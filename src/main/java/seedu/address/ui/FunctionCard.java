package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.model.task.ReadOnlyDatedTask;
import seedu.address.model.task.ReadOnlyTask;

public class FunctionCard extends UiPart{

    private static final String FXML = "FunctionListCard.fxml";
    private static final TaskStyleMapping styler = TaskPropertyMapping.getInstance();
    
    @FXML
    private HBox cardPane;
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
    private Label length;
    @FXML
    private Label recurrance;
    @FXML
    private Label tags;

    private ReadOnlyTask person;
    private int displayedIndex;

    public FunctionCard(){

    }

    public static FunctionCard load(ReadOnlyTask person, int displayedIndex){
        FunctionCard card = new FunctionCard();
        card.person = person;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    // @@author A0140155U
    @FXML
    public void initialize() {
        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");
        priority.setText(person.getPriority().toString());
        information.setText(person.getInformation().fullInformation);
        done.setText(person.getDoneFlag().toString());
        if(person.isDated()){
            ReadOnlyDatedTask datedTask = (ReadOnlyDatedTask) person;
            datetime.setText(datedTask.getDateTime().toString());
            length.setText(datedTask.getLength().toString());
            recurrance.setText(datedTask.getRecurrance().toString());
        }else{
            VBox sub = ((VBox) (datetime.getParent()));
            sub.getChildren().remove(datetime);
            sub.getChildren().remove(recurrance);
            sub.getChildren().remove(length);
            
        }
        tags.setText(person.tagsString());
        style();
    }
    
    public void style(){
        StringBuilder styleString = new StringBuilder();
        // Style based on property
        styleString.append(styler.getPriorityColour(person.getPriority().toString()));
        // Style based on done
        styleString.append(styler.getDoneColour(person.getDoneFlag().toString()));
        cardPane.setStyle(styleString.toString());
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
