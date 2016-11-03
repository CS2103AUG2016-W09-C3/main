//@@author A0139046E
package todoit.taskbook.testutil;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.model.tag.Tag;
import todoit.taskbook.model.task.*;

/**
 * A utility class to help with building DatedTask objects. Example usage: <br>
 * {@code TestDatedTask testDatedTask = new DatedTaskBuilder().withName("Attend CS2103T lecture").withDoneFlag("Not done").withDateTime("10-10-2016 1400")
 *                  .withLength("2h").withRecurrence("5d").withPriority("low").withInformation("There is webcast if you do not attend").build();}
 */
public class DatedTaskBuilder {

	private TestDatedTask datedTask;

	public DatedTaskBuilder() {
		this.datedTask = new TestDatedTask();
	}

	public DatedTaskBuilder withName(String name) throws IllegalValueException {
		this.datedTask.setName(new Name(name));
		return this;
	}

	public DatedTaskBuilder withTags(String... tags) throws IllegalValueException {
		for (String tag : tags) {
			this.datedTask.getTags().add(new Tag(tag));
		}
		return this;
	}

	public DatedTaskBuilder withPriority(String priority) throws IllegalValueException {
		this.datedTask.setPriority(new Priority(priority));
		return this;
	}

	public DatedTaskBuilder withInformation(String information) throws IllegalValueException {
		this.datedTask.setInformation(new Information(information));
		return this;
	}

	public DatedTaskBuilder withDoneFlag(String doneFlag) throws IllegalValueException {
		this.datedTask.setDoneFlag(new DoneFlag(doneFlag));
		return this;
	}

	public DatedTaskBuilder withLength(String length) throws IllegalValueException {
		this.datedTask.setLength(new Length(length));
		return this;
	}

	public DatedTaskBuilder withDateTime(String dateTimeString) throws IllegalValueException {
		this.datedTask.setDateTimeString(dateTimeString);
		return this;
	}

	public DatedTaskBuilder withRecurrence(String recurrence) throws IllegalValueException {
		this.datedTask.setRecurrence(new Recurrence(recurrence));
		return this;
	}

	public TestDatedTask build() {
		return this.datedTask;
	}
}
// @author