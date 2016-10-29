// @@author A0140155U
package seedu.address.model;

import java.util.ArrayList;

import seedu.address.commons.exceptions.StateException;

public class StatesManager implements States{
    /**
     * States are stored as a list, with currentState pointing to the current state of the task book in the model.
     */
    private ArrayList<TaskBookState> states = new ArrayList<>();
    private int currentState = 0;
    
    private final int MAX_STATES = 10; // Does not include initial state
    public final static String MESSAGE_NO_PREV_STATE = "No previous state to load.";
    public final static String MESSAGE_MAX_STATES_EXCEEDED = "Maximum undos exceeded.";
    public final static String MESSAGE_NO_NEXT_STATE = "No next state to load.";
    
    public StatesManager(TaskBookState initialState){
        states.add(initialState);
    }


    public void saveState(TaskBookState newState){
        // When a state is saved, all "future states" i.e. states that have been undone are overwritten.
        while(states.size() - 1 > currentState){
            states.remove(states.size() - 1);
        }
        
        states.add(newState);
        if(currentState == MAX_STATES){
            // Exceeded cap, delete the first state
            states.remove(0);
        }else{
            currentState++;
        }
    }


    public TaskBookState loadPreviousState() throws StateException{
        if(currentState == 0){
            assert !states.isEmpty();
            if(states.get(0).getCommand().equals(TaskBookState.INITIAL_STATE)){
                throw new StateException(MESSAGE_NO_PREV_STATE);
            }else{
                // First state is not the initial state.
                // This means it was deleted by saveState() due to hitting max capacity
                throw new StateException(MESSAGE_MAX_STATES_EXCEEDED);
            }
        }
        // Note: Unlike loadNextState(), return current state command, but previous state data.
        String commandString = getCurrentState().getCommand();
        currentState--;
        return new TaskBookState(getCurrentState().getState(), commandString);
    }

    public TaskBookState loadNextState() throws StateException{
        if(currentState == states.size() - 1){
            throw new StateException(MESSAGE_NO_NEXT_STATE);
        }
        // Note: Unlike loadNextState(), return current state command and data.
        currentState++;
        return getCurrentState();
    }

    private TaskBookState getCurrentState() {
        return states.get(currentState);
    }
    
}
//@@author