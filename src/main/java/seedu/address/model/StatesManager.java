package seedu.address.model;

import java.util.ArrayList;

import seedu.address.commons.exceptions.StateException;

public class StatesManager implements States{
    /**
     * States are stored as a list, with currentState pointing to the current state of the address book in the model.
     */
    private ArrayList<AddressBookState> states = new ArrayList<>();
    private int currentState = 0;
    
    private final String MESSAGE_NO_PREV_STATE = "No previous state to load";
    
    public StatesManager(AddressBook initialState){
        states.add(new AddressBookState(initialState, null));
    }


    public void saveState(AddressBook newState, String commandString){
        // When a state is saved, all "future states" i.e. states that have been undone are overwritten.
        while(states.size() - 1 > currentState){
            states.remove(states.size() - 1);
        }
        states.add(new AddressBookState(newState, commandString));
        currentState++;
    }


    public void loadPreviousState() throws StateException{
        if(currentState == 0){
            throw new StateException(MESSAGE_NO_PREV_STATE);
        }
        currentState--;
    }
    
    public AddressBook getAddressBook(){
        return states.get(currentState).getState();
    }
    
    public String getCommand(){
        return states.get(currentState).getCommand();
    }
}
