package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *
 */
public class PersonBuilder {

    private TestTask task;

    public PersonBuilder() {
        this.task = new TestTask();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            this.task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public PersonBuilder withInformation(String information) throws IllegalValueException {
        this.task.setInformation(new Information(information));
        return this;
    }

    public PersonBuilder withDoneFlag(String doneFlag) throws IllegalValueException {
        this.task.setDoneFlag(new DoneFlag(doneFlag));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
