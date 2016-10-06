package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

public class Priority {
    
    public final int priority;
    public static final String TASK_PRIORITY_CONSTRAINTS;
    public static final String PRIORITY_VALIDATION_REGEX;
    
    
    
    private enum acceptedPriority{
        VeryHigh(5), High(4), Medium(3), Low(2), VeryLow(1);
        private int enumPriority;
        acceptedPriority(int value){
            enumPriority = value;
        }
        int getEnumPriority(){
            return enumPriority;
        }
    }

    public Priority(String priority) throws IllegalValueException{
        assert priority != null;
        this.priority = Integer.parseInt(priority);
        try{
            
        } catch{
            
        }
        
        
    }

}
