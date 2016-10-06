package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    
    private Name name;
    private Recurrance recurring;
    private Priority priority;
    private Information information;
    private boolean autoschedule;
    

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Recurrance recurring, Priority priority, Information information, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, recurring, priority, information, tags);
        this.name = name;
        this.recurring = recurring;
        this.priority = priority;
        this.information = information;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getRecurrance(), source.getPriority(), source.getInformation(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Recurrance getRecurrance() {
        return this.recurring;
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
        return Objects.hash(name, this.recurring, this.priority, this.information, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
