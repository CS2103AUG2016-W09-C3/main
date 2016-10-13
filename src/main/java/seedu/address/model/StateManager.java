package seedu.address.model;

import java.util.ArrayList;

import seedu.address.commons.exceptions.StateException;

public class StateManager {
    private ArrayList<AddressBook> states = new ArrayList<>();
    private int currentState = 0;
    private final String MESSAGE_NO_PREV_STATE = "No previous state to load";
    
    public StateManager(AddressBook initialState){
        states.add(new AddressBook(initialState));
    }

    
    public void saveState(AddressBook newState){
        while(states.size() - 1 > currentState){
            states.remove(states.size() - 1);
        }
        states.add(new AddressBook(newState));
        currentState++;
    }


    public AddressBook loadPreviousState() throws StateException{
        if(currentState == 0){
            throw new StateException(MESSAGE_NO_PREV_STATE);
        }
        currentState--;
        return states.get(currentState);
    }
}
