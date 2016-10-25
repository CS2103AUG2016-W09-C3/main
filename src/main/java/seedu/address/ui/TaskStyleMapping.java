// @@author A0140155U
package seedu.address.ui;

/*
 * API for a class that converts Task properties to CSS properties
 * to style.
 */
public interface TaskStyleMapping {
    /* Returns a colour style based on the Task's priority property.*/
    public String getPriorityColour(String priority);
    /* Returns a colour style based on the Task's done property.*/
    public String getDoneColour(String done);
}
//@@author