package todoit.taskbook.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import todoit.taskbook.commons.events.model.PresetChangedEvent;
import todoit.taskbook.commons.events.model.TaskBookChangedEvent;
import todoit.taskbook.commons.events.storage.DataSavingExceptionEvent;
import todoit.taskbook.commons.exceptions.DataConversionException;
import todoit.taskbook.model.ReadOnlyTaskBook;
import todoit.taskbook.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskBookFilePath();

    @Override
    void setTaskBookFilePath(String filePath);

    @Override
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    @Override
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    /**
     * Saves the current version of the Task Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskBookChangedEvent(TaskBookChangedEvent abce);
    // @@author A0140155U
    /**
     * Saves the current user preferences to the hard disk.
     */
    void handlePresetChangedEvent(PresetChangedEvent event);
    // @@author
}
