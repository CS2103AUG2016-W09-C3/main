//@@author A0139947L
package guitests;

import static org.junit.Assert.assertTrue;
import static todoit.taskbook.logic.commands.DoneCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import todoit.taskbook.commons.core.Messages;
import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.logic.commands.EditCommand;
import todoit.taskbook.logic.commands.FindCommand;
import todoit.taskbook.logic.parser.CommandParser;
import todoit.taskbook.logic.parser.ParsedCommand;
import todoit.taskbook.model.task.DateTime;
import todoit.taskbook.model.task.Information;
import todoit.taskbook.model.task.Length;
import todoit.taskbook.model.task.Name;
import todoit.taskbook.model.task.Priority;
import todoit.taskbook.model.task.Recurrence;
import todoit.taskbook.model.task.TimeInterval;
import todoit.taskbook.testutil.TestDatedTask;
import todoit.taskbook.testutil.TestTask;
import todoit.taskbook.testutil.TestUtil;

public class EditCommandTest extends TaskBookGuiTest {
    
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
        TestTask[] listToEdit = td.getTypicalTasks();
        String command = "edit 1 p/h";
        assertEditSuccess(command, listToEdit);
    }
    
    @Test
    public void edit_invalidPriorityVeryHigh(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalTasks();
        String command = "edit 2 p/superhigh";
        unknownCommandFormatPriority(command);
    }
    
    @Test
    public void edit_priorityVeryLow(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalTasks();
        
        String command = "edit 1 p/vl";
        assertEditSuccess(command, listToEdit);
    }
    
    @Test
    public void edit_priorityCaseSensitive(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalTasks();
        
        String command = "edit 3 p/vH";
        assertEditSuccess(command, listToEdit);
    }
    
    @Test
    public void edit_invalidTimeIntervalRecurrence(){
        // Check if edit function edits successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        TestTask datedTaskToAdd2 = td.csFinalExam;
        String command = "edit 9 r/3dayzz";
        unknownCommandFormatTimeInterval(command);
    }
    
    @Test
    public void edit_datedTaskTimeIntervalRecurrenceDays(){
        // Check if program is able to edit all tasks in sequence
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        TestTask datedTaskToAdd2 = td.csFinalExam;
        String command = "edit 8 n/Meet Isabel p/High r/3days d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd, datedTaskToAdd2);
        assertEditSuccess(command, finalList);
    }
    
    @Test
    public void edit_datedTaskTimeIntervalRecurrenceWeeks(){
        // Check if program is able to edit all tasks in sequence
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        TestTask datedTaskToAdd2 = td.csFinalExam;
        String command = "edit 8 n/Meet Isabel p/High r/6weeks d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd, datedTaskToAdd2);
        assertEditSuccess(command, finalList);
    }
    
    @Test
    public void edit_recurrenceAndPriorityAlias(){
        // Check if program is able to edit all tasks in sequence
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        TestTask datedTaskToAdd2 = td.csFinalExam;
        String command = "edit 8 n/Meet KappaRoss p/vH r/6weeks d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd, datedTaskToAdd2);
        assertEditSuccess(command, finalList);
    }
    
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
    
    private TestTask[] doEdit(String args, TestTask[] list) {
        // To get different commands from the command input
        String[] split = args.split("\\s+");
        int index = Integer.parseInt(split[1]);
        TestTask target = list[index-1];
        
        ParsedCommand command = new CommandParser(args);
        String name, datetime, length, recurrence, priority, information;
        name = command.getParamOrDefault("n", "");
        datetime = command.getParamOrDefault("d", "-1");
        length = command.getParamOrDefault("l", "-1");
        recurrence = command.getParamOrDefault("r", Recurrence.NO_INTERVAL);
        priority = command.getParamOrDefault("p", "");
        information = command.getParamOrDefault("i", "");
        
        TestTask[] expected = TestUtil.removeTaskFromList(list, index);
        
        try {
            expected = testEdit(target, name, datetime, length, recurrence, priority, information, expected, index);
        } catch (IllegalValueException e) {
            assert(false);
        }
        return expected;
    }

    // Helper method to testEdit in doEdit(String args, TestTask[] list)
    private TestTask[] testEdit(TestTask target, String name, String datetime, String length, String recurrence,
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
        
        if (!recurrence.equals(Recurrence.NO_INTERVAL)){
            ((TestDatedTask) target).setRecurrence(new Recurrence(recurrence));
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
//@@author