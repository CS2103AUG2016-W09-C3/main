// @@author A0140155U
package todoit.taskbook.model;

import todoit.taskbook.commons.exceptions.StateException;

/**
 * The API of the States component.
 */
public interface States {
    /** Saves a state to the state list. */
    public void saveState(TaskBookState newState);
    
    /** Loads the previous state. */
    public TaskBookState loadPreviousState() throws StateException;

    /** Loads the next state. */
    public TaskBookState loadNextState() throws StateException;
    
}
//@@author