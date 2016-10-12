package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            this.task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withInformation(String information) throws IllegalValueException {
        this.task.setInformation(new Information(information));
        return this;
    }

    public TaskBuilder withDoneFlag(String doneFlag) throws IllegalValueException {
        this.task.setDoneFlag(new DoneFlag(doneFlag));
        return this;
    }
    
    public TaskBuilder withLength(String length) throws IllegalValueException {
        this.task.setLength(new Length(length));
        return this;
    }
    
    public TaskBuilder withDateTime(String datetime) throws IllegalValueException {
        this.task.setDateTime(new DateTime(datetime));
        return this;
    }
    
    public TaskBuilder withRecurrance(String recurrance) throws IllegalValueException {
        this.task.setRecurrance(new Recurrance(recurrance));
        return this;
    }

    public TestTask build() {
        return this.task;
    }
}
