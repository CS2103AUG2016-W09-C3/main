package seedu.address.model;

import java.util.ArrayList;

import seedu.address.commons.exceptions.StateException;

public class StatesManager implements States{
    /**
     * States are stored as a list, with currentState pointing to the current state of the address book in the model.
     */
    private ArrayList<AddressBookState> states = new ArrayList<>();
    private int currentState = 0;
    
    private final int MAX_STATES = 10; // Does not include initial state
    private final String MESSAGE_NO_PREV_STATE = "No previous state to load.";
    private final String MESSAGE_MAX_STATES_EXCEEDED = "Maximum undos exceeded.";
    private final String MESSAGE_NO_NEXT_STATE = "No next state to load.";
    
    public StatesManager(AddressBookState initialState){
        states.add(initialState);
    }


    public void saveState(AddressBookState newState){
        // When a state is saved, all "future states" i.e. states that have been undone are overwritten.
        while(states.size() - 1 > currentState){
            states.remove(states.size() - 1);
        }
        states.add(newState);
        if(currentState == MAX_STATES){
            states.remove(0);
        }else{
            currentState++;
        }
    }


    public AddressBookState loadPreviousState() throws StateException{
        if(currentState == 0){
            if(states.get(0).getCommand().equals(AddressBookState.INITIAL_STATE)){
                throw new StateException(MESSAGE_NO_PREV_STATE);
            }else{
                throw new StateException(MESSAGE_MAX_STATES_EXCEEDED);
            }
        }
        String commandString = states.get(currentState).getCommand();
        currentState--;
        return new AddressBookState(states.get(currentState).getState(), commandString);
    }
    
    public AddressBookState loadNextState() throws StateException{
        if(currentState == states.size() - 1){
            throw new StateException(MESSAGE_NO_NEXT_STATE);
        }
        currentState++;
        return states.get(currentState);
    }
}
