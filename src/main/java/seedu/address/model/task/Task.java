//@@author A0139121R
package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    
    protected Name name;
    protected Priority priority;
    protected Information information;
    protected DoneFlag doneFlag;

    protected UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Priority priority, Information information, DoneFlag doneFlag, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, information, doneFlag, tags);
        this.name = name;
        this.priority = priority;
        this.information = information;
        this.doneFlag = doneFlag;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriority(), source.getInformation(), source.getDoneFlag(), source.getTags());
    }
    /**
     * Default constructor for DatedTask subclass, should not be used.
     */
    protected Task(){
        ;
    }

    @Override
    public Name getName() {
        return name;
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
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    //@@ author
    @Override
    public DoneFlag getDoneFlag() {
        return this.doneFlag;
    }
    //@@author A0139121R
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, this.priority, this.information, this.doneFlag, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
//@@ author