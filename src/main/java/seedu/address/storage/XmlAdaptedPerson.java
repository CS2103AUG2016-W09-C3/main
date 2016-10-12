package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedPerson {

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
    private String recurrance;
    @XmlElement
    private String done;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private boolean isDated;
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyTask source) {
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
            recurrance = datedSource.getRecurrance().toString();
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final Information information = new Information(this.information);
        final DoneFlag done = new DoneFlag(this.done);
        final UniqueTagList tags = new UniqueTagList(personTags);
        if(isDated){
            final DateTime dateTime = new DateTime(this.dateTime, true);
            final Length length = new Length(this.length);
            final Recurrance recurrance = new Recurrance(this.recurrance);
            return new DatedTask(name, dateTime, length, recurrance, priority, information, done, tags);
        }else{
            return new Task(name, priority, information, done, tags);
        }
    }
}
