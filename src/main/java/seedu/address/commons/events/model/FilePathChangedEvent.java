package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/** Indicates the filepath of the AddressBook has changed*/
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
