package todoit.taskbook.model;


import java.util.List;

import todoit.taskbook.model.tag.Tag;
import todoit.taskbook.model.tag.UniqueTagList;
import todoit.taskbook.model.task.ReadOnlyTask;
import todoit.taskbook.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an task book
 */
public interface ReadOnlyTaskBook {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
