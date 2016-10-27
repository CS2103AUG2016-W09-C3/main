package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.model.task.ReadOnlyDatedTask;
import seedu.address.model.task.ReadOnlyTask;

public class PersonCard extends UiPart{

    private static final String FXML = "PersonListCard.fxml";
    private static final TaskStyleMapping styler = TaskPropertyMapping.getInstance();
    
    @FXML
    private HBox cardPane;

    @FXML
    private VBox light;
    
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

    public PersonCard(){

    }

    public static PersonCard load(ReadOnlyTask person, int displayedIndex){
        PersonCard card = new PersonCard();
        card.person = person;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    // @@author A0140155U
    @FXML
    public void initialize() {
        name.setText(person.getName().fullName);
        id.setText(Integer.toString(displayedIndex));
        setOrNullText(priority, "Priority: ", person.getPriority().toString());
        setOrNullText(information, "Info: ", person.getInformation().toString());
        setOrNullText(done, person.getDoneFlag().toString());
        if(person.isDated()){
            ReadOnlyDatedTask datedTask = (ReadOnlyDatedTask) person;
            setOrNullText(datetime, "Date: ",
                    datedTask.getDateTime().toString() + (datedTask.hasValidLength() ? " - " + datedTask.getDateTimeEnd().toString() : ""));
            setOrNullText(length, "");
            setOrNullText(recurrance, "Repeat every ", datedTask.getRecurrance().toString());
        }else{
            setOrNullText(datetime, "");
            setOrNullText(length, "");
            setOrNullText(recurrance, "");
        }
        tags.setText(person.tagsString());
        removeUnnecessaryLabels();
        style();
    }

    public void setOrNullText(Label label, String value){
        setOrNullText(label, "", value);
    }
    
    public void setOrNullText(Label label, String prefix, String value){
        if(value.equals("")){
            label.setText("");
        }else{
            label.setText(prefix + value);
        }
    }
    
    public void removeUnnecessaryLabels(){
        VBox sub = ((VBox) (datetime.getParent()));
        sub.getChildren().removeIf(lbl -> lbl instanceof Label && ((Label) lbl).getText().equals(""));
    }
    
    public void style(){
        StringBuilder styleString = new StringBuilder();
        // Style based on property
        styleString.append(styler.getPriorityColour(person.getPriority().toString()));
        // Style based on done
        styleString.append(styler.getDoneColour(person.getDoneFlag().toString()));
        light.setStyle(styleString.toString());
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
