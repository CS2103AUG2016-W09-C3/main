package todoit.taskbook.model;
//@@author A0140155U
/**
 * Represents a state of the task book.
 * 
 * An task book state consists of a state and a command.
 * The state stores the state of the task book at the point the command was executed.
 * The command stores the command that was executed to achieve this state.
 * 
 * Used simply as a wrapper for two variables.
 */
public class TaskBookState {
    private final TaskBook state;
    private final String command;
    public static final String INITIAL_STATE = "Initial state";
    
    public TaskBookState(TaskBook state){
        this(state, INITIAL_STATE);
    }
    
    public TaskBookState(TaskBook state, String command){
        this.state = new TaskBook(state);
        this.command = command;
    }
    
    public TaskBook getState(){
        return state;
    }
    
    public String getCommand(){
        return command;
    }
}
//@@author
