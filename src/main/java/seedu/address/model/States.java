package seedu.address.model;

import seedu.address.commons.exceptions.StateException;

public interface States {
    public void saveState(AddressBook newState, String commandString);
    public void loadPreviousState() throws StateException;
    public AddressBook getAddressBook();
    public String getCommand();
}
