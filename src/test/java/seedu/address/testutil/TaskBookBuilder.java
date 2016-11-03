package seedu.address.testutil;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.model.TaskBook;
import todoit.taskbook.model.tag.Tag;
import todoit.taskbook.model.task.Task;
import todoit.taskbook.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskBook objects.
 * Example usage: <br>
 *     {@code TaskBook ab = new TaskBookBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskBookBuilder {

    private TaskBook taskBook;

    public TaskBookBuilder(TaskBook taskBook){
        this.taskBook = taskBook;
    }

    public TaskBookBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        return this;
    }

    public TaskBookBuilder withTag(String tagName) throws IllegalValueException {
        taskBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskBook build(){
        return taskBook;
    }
}
