package seedu.address.model;

import seedu.address.commons.exceptions.StateException;

/**
 * The API of the States component.
 */
public interface States {
    /** Saves a state to the state list. */
    public void saveState(AddressBook newState, String commandString);
    
    /** Loads the previous state. Note: does not return the state. */
    public void loadPreviousState() throws StateException;
    
    /** Gets the address book attached to the current state */
    public AddressBook getAddressBook();
    
    /** Gets the command string attached to the current state */
    public String getCommand();
}
