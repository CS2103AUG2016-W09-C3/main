package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task with date, time and length in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */

public class DatedTask extends Task implements ReadOnlyDatedTask {
    
    private DateTime dateTime;
    private Length length;
    private Recurrance recurrance;
    
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
        this.dateTime = time;
        this.length = length;
        this.recurrance = recurring;
    }
    
    /**
     * Copy constructor.
     */
    public DatedTask(ReadOnlyDatedTask source) {
        this(source.getName(), source.getDateTime(), source.getLength(), 
             source.getRecurrance(), source.getPriority(), source.getInformation(), source.getTags());
    }
    
    public DateTime getDateTime(){
        return this.dateTime;
    }
    
    public Length getLength(){
        return this.length;
    }

    public Recurrance getRecurrance() {
        return this.recurrance;
    }

    @Override
    public String toString() {
        return getAsText();
    }
    

    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.name, this.dateTime, this.length, 
                            this.recurrance, this.priority, this.information, tags);
    }
    
}
