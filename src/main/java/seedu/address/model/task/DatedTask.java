package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task with date, time and length in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */

public class DatedTask extends Task implements ReadOnlyTask{
    
    private Name name;
    private Time time;
    private Date date;
    private Length length;
    private Recurrance recurring;
    private Priority priority;
    private Information information;
    
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
    public DatedTask(Name name, Time time, Date date, Length length, Recurrance recurring, Priority priority,
            Information information, UniqueTagList uniqueTagList) {
        assert !CollectionUtil.isAnyNull(name, recurring, priority, information, tags);
        this.name = name;
        this.time = time;
        this.date = date;
        this.length = length;
        this.recurring = recurring;
        this.priority = priority;
        this.information = information;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public Time getTime(){
        return this.time;
    }
    
    public Date getDate(){
        return this.date;
    }
    
    public Length getLength(){
        return this.length;
    }
    
    @Override
    public Name getName() {
        return this.name;
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
                .append(" Date: ")
                .append(getDate())
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
        return Objects.hash(this.name, this.time, this.date, this.length, 
                            this.recurring, this.priority, this.information, tags);
    }
    
}
