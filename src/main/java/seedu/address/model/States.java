package seedu.address.model;

import seedu.address.commons.exceptions.StateException;

/**
 * The API of the States component.
 */
public interface States {
    /** Saves a state to the state list. */
    public void saveState(AddressBookState newState);
    
    /** Loads the previous state. */
    public AddressBookState loadPreviousState() throws StateException;

    /** Loads the next state. */
    public AddressBookState loadNextState() throws StateException;
    
}
