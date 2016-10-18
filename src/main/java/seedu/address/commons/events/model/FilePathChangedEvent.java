package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

public class FilePathChangedEvent extends BaseEvent {

    public final String filePath;

    public FilePathChangedEvent (String filePath){
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "new file path: " + filePath;
    }
}
