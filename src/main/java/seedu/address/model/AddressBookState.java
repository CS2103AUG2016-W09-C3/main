package seedu.address.model;
//@@author A0140155U
/**
 * Represents a state of the address book.
 * 
 * An address book state consists of a state and a command.
 * The state stores the state of the address book at the point the command was executed.
 * The command stores the command that was executed to achieve this state.
 * 
 * Used simply as a wrapper for two variables.
 */
public class AddressBookState {
    private final AddressBook state;
    private final String command;
    public static final String INITIAL_STATE = "Initial state";
    
    public AddressBookState(AddressBook state){
        this(state, INITIAL_STATE);
    }
    
    public AddressBookState(AddressBook state, String command){
        this.state = new AddressBook(state);
        this.command = command;
    }
    
    public AddressBook getState(){
        return state;
    }
    
    public String getCommand(){
        return command;
    }
}
//@@author