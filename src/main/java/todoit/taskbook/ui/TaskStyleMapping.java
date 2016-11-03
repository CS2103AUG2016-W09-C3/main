// @@author A0140155U
package todoit.taskbook.ui;

/*
 * API for a class that converts Task properties to CSS properties
 * to style.
 */
public interface TaskStyleMapping {
    /* Returns a colour style based on the Task's priority property.*/
    public String getLightPriorityColour(String priority);
    /* Returns a colour style based on the Task's done property.*/
    public String getLightDoneColour(String done);
    /* Returns a colour style based on the Task's done property.*/
    public String getCardDoneColour(String done);
}
//@@author