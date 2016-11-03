package todoit.taskbook.storage;

import javax.xml.bind.annotation.XmlElement;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.model.tag.Tag;
import todoit.taskbook.model.tag.UniqueTagList;
import todoit.taskbook.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String information;
    @XmlElement
    private String dateTime;
    @XmlElement
    private String length;
    @XmlElement
    private String recurrence;
    @XmlElement
    private String done;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private boolean isDated;
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}
    
    // @@author A0140155U

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        priority = source.getPriority().toString();
        information = source.getInformation().fullInformation;
        done = source.getDoneFlag().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        
        isDated = source.isDated();
        if(isDated){
            ReadOnlyDatedTask datedSource = (ReadOnlyDatedTask) source;
            dateTime = datedSource.getDateTime().toString();
            length = datedSource.getLength().toString();
            recurrence = datedSource.getRecurrence().toString();
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final Information information = new Information(this.information);
        final DoneFlag done = new DoneFlag(this.done);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        if(isDated){
            final DateTime dateTime = new DateTime(this.dateTime, true);
            final Length length = new Length(this.length);
            final Recurrence recurrence = new Recurrence(this.recurrence);
            return new DatedTask(name, dateTime, length, recurrence, priority, information, done, tags);
        }else{
            return new Task(name, priority, information, done, tags);
        }
    }
    
    // @@author
}
