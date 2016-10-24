package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.ParsedCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.DoneFlag;
import seedu.address.model.task.Information;
import seedu.address.model.task.Length;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Recurrance;
import seedu.address.model.task.TimeInterval;
import seedu.address.testutil.TestDatedTask;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class EditCommandTest extends AddressBookGuiTest {
    
    @Test
    public void edit_nonDatedTask(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalTasks();
        String command = "edit 1 n/King Arthur p/high i/This is just another kind of information you would see in a task";
        assertEditSuccess(command, listToEdit);
    }
    
    @Test
    public void edit_priorityHigh(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalPersons();
        String command = "edit 1 p/h";
        assertEditSuccess(command, listToEdit);
    }
    
    @Test
    public void edit_invalidPriorityVeryHigh(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalPersons();
        String command = "edit 2 p/superhigh";
        unknownCommandFormatPriority(command);
    }
    
    @Test
    public void edit_priorityVeryLow(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalPersons();
        String command = "edit 1 p/vl";
        assertEditSuccess(command, listToEdit);
    }
/*    
    @Test
    public void edit_timeIntervalRecurrenceDays(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestDatedTask[] listToEdit = td.getTypicalDatedTask();
        String command = "edit 1 r/7day";
        assertEditSuccess(command, listToEdit);
    }
    
    @Test
    public void edit_timeIntervalRecurrenceWeeks(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestDatedTask[] listToEdit = td.getTypicalDatedTask();
        String command = "edit 1 r/16weeks";
        assertEditSuccess(command, listToEdit);
    }
    
    @Test
    public void edit_invalidTimeIntervalRecurrence(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestDatedTask[] listToEdit = td.getTypicalDatedTask();
        String command = "edit 1 r/3dayzz";
        unknownCommandFormatTimeInterval(command);
    }
*/    
    @Test
    public void edit_invalidIndex(){
        // Check if program handles invalid index properly with a thrown message
        commandBox.runCommand("clear");
        String command = "edit 1 n/King Arthur's birthday i/This is just another information";
        assertInvalidIndex(command);

    }
    
    @Test
    public void edit_invalidEditCommandFormat(){
        // Check if program handles invalid edit format properly with a thrown message
        String command = "edit";
        assertInvalidEditCommandFormat(command);
    }
    
    
    @Test
    public void edit_nonDatedTaskNotInSequence(){
        // Check if program is able to edit a task in the middle of list
        TestTask[] listToEdit = td.getTypicalTasks();
        String command = "edit 3 i/This is also an information p/high";
        assertEditSuccess(command, listToEdit);
    }
   
    @Test
    public void edit_datedTask(){
        // Check if program is able to edit all tasks in sequence
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        TestTask datedTaskToAdd2 = td.csFinalExam;
        String command = "edit 8 n/Meet Isabel p/High r/6d d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd, datedTaskToAdd2);
        assertEditSuccess(command, finalList);
    }
  
    /**
     * Runs the edit command to edit the task at specified index and confirms the task is successfully edited
     * @param editCommand: To edit the first person in the list, 1 should be given as the target index. (i.e. Edit 1 ... )
     * @param listToEdit: A list of the current tasks
     * @return 
     */
    
    
    private TestTask[] doEdit(String args, TestTask[] list) {
        // To get different commands from the command input
        String[] split = args.split("\\s+");
        int index = Integer.parseInt(split[1]);
        TestTask target = list[index-1];
        
        ParsedCommand command = new CommandParser(args);
        String name, datetime, length, recurrance, priority, information;
        name = command.getParamOrDefault("n", "");
        datetime = command.getParamOrDefault("d", "-1");
        length = command.getParamOrDefault("l", "-1");
        recurrance = command.getParamOrDefault("r", Recurrance.NO_INTERVAL);
        priority = command.getParamOrDefault("p", "");
        information = command.getParamOrDefault("i", "");
        
        TestTask[] expected = TestUtil.removeTaskFromList(list, index);
        
        try {
            expected = testEdit(target, name, datetime, length, recurrance, priority, information, expected, index);
        } catch (IllegalValueException e) {
            assert(false);
        }
        return expected;
    }

    // Helper method to testEdit in doEdit(String args, TestTask[] list)
    private TestTask[] testEdit(TestTask target, String name, String datetime, String length, String recurrance,
            String priority, String information, TestTask[] expected, int index) throws IllegalValueException {
        boolean isChanged = false;
        
        if (!name.equals("")){
            target.setName(new Name(name));
        }
        
        if (!datetime.equals("-1")){
            ((TestDatedTask) target).setDateTimeString(datetime);
        }
        
        if (!length.equals("-1")){
        	((TestDatedTask) target).setLength(new Length(length));
        }
        
        if (!recurrance.equals(Recurrance.NO_INTERVAL)){
            ((TestDatedTask) target).setRecurrance(new Recurrance(recurrance));
        }
        
        if (!priority.equals("")){
            target.setPriority(new Priority(priority));
        }
        
        if (!information.equals("")){
            target.setInformation(new Information(information));
        }

        if (isChanged) {
            ((TestDatedTask) target).setDateTime(new DateTime(datetime));
        }
        expected = TestUtil.addTaskToListIndex(expected, target, index - 1);
        return expected;
    }
    
    private void assertEditSuccess(String command, TestTask[] list) {

        String[] split = command.split("\\s+");
        int index = Integer.parseInt(split[1]);
        TestTask target = list[index-1];
        
        TestTask[] expected = doEdit(command, list);
        commandBox.runCommand(command);
        
        // Confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expected));
        
        // Confirm the result message is correctly edited
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, target));
    }
    
    private void assertInvalidIndex(String command) {
        commandBox.runCommand(command);
        // Throws error message if index is invalid
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
    }
    
    private void assertInvalidEditCommandFormat(String command) {
        commandBox.runCommand(command);
        // Throws error message if format is invalid
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }
    
    private void unknownCommandFormatPriority(String command) {
        commandBox.runCommand(command);
        // Throws error message if format is invalid
        assertResultMessage(String.format(Priority.MESSAGE_PRIORITY_CONSTRAINTS, EditCommand.MESSAGE_USAGE));
    }
    
    private void unknownCommandFormatTimeInterval(String command) {
        commandBox.runCommand(command);
        // Throws error message if format is invalid
        assertResultMessage(String.format(TimeInterval.MESSAGE_TIME_INTERVAL_CONSTRAINTS, EditCommand.MESSAGE_USAGE));
    }
}