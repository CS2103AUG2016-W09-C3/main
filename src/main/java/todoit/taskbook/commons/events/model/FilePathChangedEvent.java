// @@author A0140155U
package todoit.taskbook.commons.events.model;

import todoit.taskbook.commons.events.BaseEvent;

/** Indicates the filepath of the TaskBook has changed*/
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
//@@author
