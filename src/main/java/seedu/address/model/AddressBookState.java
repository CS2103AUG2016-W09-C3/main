package seedu.address.model;

/**
 * Represents a state of the address book.
 * 
 */
public class AddressBookState {
    private final AddressBook state;
    private final String command;
    
    public AddressBookState(AddressBook state, String command){
        this.state = state;
        this.command = command;
    }
    
    public AddressBook getState(){
        return state;
    }
    
    public String getCommand(){
        return command;
    }
}
