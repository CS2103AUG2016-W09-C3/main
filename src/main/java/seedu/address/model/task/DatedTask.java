package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task with date, time and length in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */

public class DatedTask extends Task {
    
    private DateTime time;
    private Length length;
    private Recurrance recurring;
    
    private boolean autoSchedule;
    
    private UniqueTagList tags;
    
    /**
     * Dafault constructor, should not be used.
     */
    protected DatedTask(){
        ;
    }
    
    /**
     * Every field must be present and not null.
     */
    public DatedTask(Name name, DateTime time, Length length, Recurrance recurring, Priority priority,
            Information information, UniqueTagList uniqueTagList) {
        super(name, priority, information, uniqueTagList);
        assert !CollectionUtil.isAnyNull(name, recurring, priority, information, tags);
        this.time = time;
        this.length = length;
        this.recurring = recurring;
    }
    
    public DateTime getTime(){
        return this.time;
    }
    
    public Length getLength(){
        return this.length;
    }

    public Recurrance getRecurrance() {
        return this.recurring;
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    /**
     * Formats the task as text, showing all contact details.
     */
    @Override
    public String getAsText(){
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getTime())
                .append(" Length: ")
                .append(getLength())
                .append(" Recurrance: ")
                .append(getRecurrance())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Information: ")
                .append(getInformation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.name, this.time, this.length, 
                            this.recurring, this.priority, this.information, tags);
    }
    
}
