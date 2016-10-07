package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * 
 * Represents priority of a Task or DatedTask object in task book.
 * priority stored as acceptedPriority enum.
 * Guarantees: immutable;
 */
public class Priority {
    
    public final acceptedPriority priority;
    
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should either veryhigh, high, medium, low or verylow./n"
                                                            + "It is case insensitive";
    
    
    
    private enum acceptedPriority{
        VERYHIGH(5), HIGH(4), MEDIUM(3), LOW(2), VERYLOW(1);
        
        private int enumPriority;
        
        private acceptedPriority(int value){
            this.enumPriority = value;
        }
        public int getEnumPriority(){
            return enumPriority;
        }
        
        public static boolean Contains(String test){
            for(acceptedPriority enumValue : acceptedPriority.values()){
                if(enumValue.name() == test){
                    return true;
                }
            }
            return false;
        }
    }
    
    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is not in valid set of enum.
     */
    public Priority(String priority) throws IllegalValueException{
        assert priority != null;
        priority = priority.trim();
        String upperPriority = priority.toUpperCase();
        if(acceptedPriority.Contains(upperPriority)){
            this.priority = acceptedPriority.valueOf(upperPriority);
        } else {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
    }
    
    @Override
    public String toString() {
        return this.priority.name();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority.name().equals(((Priority) other).priority.name())); // state check
    }

    @Override
    public int hashCode() {
        return this.priority.name().hashCode();
    }

}
