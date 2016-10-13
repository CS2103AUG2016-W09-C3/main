package seedu.address.model;

import java.util.ArrayList;

public class StateManager {
    private ArrayList<AddressBook> states = new ArrayList<>();
    private int currentState = 0;
    
    public StateManager(){
        
    }
    
    
    public void saveState(AddressBook newState){
        while(states.size() > currentState){
            states.remove(states.size() - 1);
        }
        states.add(new AddressBook(newState));
        currentState++;
        
    }
}
