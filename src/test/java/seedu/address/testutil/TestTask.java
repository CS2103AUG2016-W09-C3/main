package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    protected Name name;
    protected Priority priority;
    protected Information information;
    protected DoneFlag doneFlag;
    protected UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void setDoneFlag(DoneFlag doneFlag) {
        this.doneFlag = doneFlag;
    }
    
    @Override
    public Name getName() {
        return this.name;
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public Information getInformation() {
        return this.information;
    }

    @Override
    public DoneFlag getDoneFlag() {
        return this.doneFlag;
    }

    @Override
    public UniqueTagList getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("p/" + this.getPriority().toString() + " ");
        sb.append("i/" + this.getInformation().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }   
}
