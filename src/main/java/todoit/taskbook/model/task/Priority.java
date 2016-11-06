//@@author A0139121R
package todoit.taskbook.model.task;

import java.util.HashMap;

import todoit.taskbook.commons.exceptions.IllegalValueException;

/**
 * 
 * Represents priority of a Task or DatedTask object in task book.
 * priority stored as acceptedPriority enum.
 * Guarantees: immutable;
 */
public class Priority {
    
    public final acceptedPriority priority;
    
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should either veryhigh, high, medium, low or verylow.\n"
                                                            + "It is case insensitive.\n" + "You may also type in vh/h/m/l/vl in that respective order.";
    
    public static final String DEFAULT_VALUE = "medium";
    
    private static HashMap<String, String> listOfPriorities = new HashMap<>();
    
    private enum acceptedPriority{
        VERYHIGH(5, "Very high"), HIGH(4, "High"), MEDIUM(3, "Medium"), LOW(2, "Low"), VERYLOW(1, "Very low");
        
        private final int enumPriority;
        private final String displayedAlias;
        
        private acceptedPriority(int value, String displayedAlias){
            this.enumPriority = value;
            this.displayedAlias = displayedAlias;
        }
        public int getEnumPriority(){
            return enumPriority;
        }
        
        public String getDisplayedAlias(){
            return displayedAlias;
        }
        
        public static boolean Contains(String test){
            for(acceptedPriority enumValue : acceptedPriority.values()){
                if(enumValue.name().equals(test)){
                    return true;
                }
            }
            return false;
        }
    }
    //@@author
    //@@author A0139947L
    // User is able to put different aliases for priority
    private void getListOfDifferentAliases() {
        listOfPriorities.put("VH", "VERYHIGH");
        listOfPriorities.put("H", "HIGH");
        listOfPriorities.put("M", "MEDIUM");
        listOfPriorities.put("L", "LOW");
        listOfPriorities.put("VL", "VERYLOW");
    }
    //@@author
    
    public String getDisplayedAlias(){
        return priority.getDisplayedAlias();
    }
    
    //@@author A0139121R
    public int getEnumPriority(){
        return priority.getEnumPriority();
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
        //@@author
        //@@author A0139947L
        listOfPriorities = new HashMap<String, String>();
        getListOfDifferentAliases();
        
        if (listOfPriorities.containsKey(upperPriority)){
            upperPriority = listOfPriorities.get(upperPriority);
        }
        //@@author
        //@@author A0139121R
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
