//@@author A0139046E
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable dated task object. For testing only.
 */
public class TestDatedTask extends TestTask implements ReadOnlyDatedTask {

    private Length length;
    private DateTime dateTime;
    private Recurrance recurrance;
    private String dateTimeString;
    private UniqueTagList tags;

    public TestDatedTask() {
        tags = new UniqueTagList();
    }
    
    public void setLength(Length length) {
        this.length = length; 
    }
    
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public void setRecurrance(Recurrance recurrance) {
        this.recurrance = recurrance; 
    }
    
    public void setDateTimeString(String dateTimeString){
    	this.dateTimeString = dateTimeString;
    }

    @Override
    public UniqueTagList getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    public Length getLength() {
        return this.length;
    }
    
    @Override
    public DateTime getDateTime() {
        try {
			dateTime = new DateTime(dateTimeString);
		} catch (IllegalValueException e) {
			assert false: "not possible";
		}
		return dateTime;
    }
    
    @Override
    public Recurrance getRecurrance() {
        return this.recurrance;
    }
    
    public String getDateTimeString() {
    	return this.dateTimeString;
    }
    
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("d/" + this.getDateTimeString() + " ");
        sb.append("l/" + this.getLength().toString() + " ");
        sb.append("r/" + this.getRecurrance().toString() + " ");
        sb.append("p/" + this.getPriority().toString() + " ");
        sb.append("i/" + this.getInformation().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public DateTime getDateTimeEnd() {
        if(!hasValidLength()){
            return dateTime;
        }
        return new DateTime(dateTime.getDateTime().plusMinutes(length.getAsMinutes()));
    }

    @Override
    public boolean hasValidLength() {
        return length.isValid();
    }
}
//@@author
