//@@author A0139947L
package guitests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import todoit.taskbook.commons.core.Messages;
import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.logic.commands.EditCommand;
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
    public void editCommand_nonDatedTask_Success(){
        // Check if edit function edits non-dated task parameters successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalTasks();
        
        String command = "edit 1 n/King Arthur p/high i/This is just another kind of information you would see in a task";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 1 n/For camelot";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 1 p/verylow";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 1 i/New information";
        assertEditSuccess(command, listToEdit);
    }
    
    @Test
    public void editCommand_datedTask_Success(){
        // Check if edit function edits dated task parameters successfully (not necessary in sequence)
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        String command = "edit 8 n/For camelot";
        assertEditSuccess(command, finalList);
        
        finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        command = "edit 8 d/tmr 9pm l/3h";
        assertEditSuccess(command, finalList);
        
        finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        command = "edit 8 r/2d";
        assertEditSuccess(command, finalList);
        
        finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        command = "edit 8 p/veryhigh";
        assertEditSuccess(command, finalList);
        
        finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        command = "edit 8 i/New information (Dated task)";
        assertEditSuccess(command, finalList);
    }
    
    @Test
    public void editCommand_priority_Success(){
        // Check if edit function edits priority successfully (From very high to very low)
        TestTask[] listToEdit = td.getTypicalTasks();
        
        String command = "edit 1 p/vh";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 1 p/h";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 1 p/m";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 1 p/l";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 1 p/vl";
        assertEditSuccess(command, listToEdit);
    }
    
    @Test
    public void editCommand_invalidPriority_UnknownCommandFormat(){
        // Check if edit function throws unknown command properly (priority)
        String command = "edit 2 p/superhigh";
        unknownCommandFormatPriority(command);
        
        command = "edit 2 p/hihg";
        unknownCommandFormatPriority(command);
        
        command = "edit 2 p/p/superhigh";
        unknownCommandFormatPriority(command);
    }
    
    @Test
    public void editCommand_priorityCaseSensitive_Success(){
        // Check if edit function is able to take in case insensitive priorities
        TestTask[] listToEdit = td.getTypicalTasks();
        
        String command = "edit 3 p/vH";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 3 p/M";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 3 p/VERYHIGH";
        assertEditSuccess(command, listToEdit);
        
        command = "edit 3 p/vErYlOw";
        assertEditSuccess(command, listToEdit);
    }
    
    
    // PROBLEM WITH THIS
    @Test
    public void editCommand_invalidTimeIntervalRecurrence_UnknownCommandFormat(){
        // Check if edit function throws unknown command properly (recurrence)
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        String command = "edit 8 r/3dayzz";
        unknownCommandFormatTimeInterval(command);
        
        command = "edit 8 r/ThreeDays";
        unknownCommandFormatTimeInterval(command);
    }
    
    @Test
    public void editCommand_datedTaskTimeIntervalRecurrenceDays_Success(){
        // Check if program is able to edit all tasks in sequence
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        TestTask datedTaskToAdd = td.csFinalExam;
        
        String command = "edit 8 n/Meet Isabel p/High r/3days d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        
        assertEditSuccess(command, finalList);
    }
    
    @Test
    public void editCommand_datedTaskTimeIntervalRecurrenceWeeks_Success(){
        // Check if program is able to edit all tasks in sequence
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        
        String command = "edit 8 n/Meet Isabel p/High r/6weeks d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        
        assertEditSuccess(command, finalList);
    }
    
    @Test
    public void editCommand_priorityAlias_Success(){
        // Check if edit function is able to take in aliases for recurrence
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        
        String command = "edit 8 n/Meet KappaRoss p/vl r/6w d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        assertEditSuccess(command, finalList);
        
        command = "edit 8 n/Meet KappaRoss p/h r/6w d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        assertEditSuccess(command, finalList);
    }
    
    @Test
    public void editCommand_recurrenceAlias_Success(){
        // Check if edit function is able to take in aliases for priority
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        
        String command = "edit 8 n/Meet KappaRoss p/veryhigh r/6week d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        assertEditSuccess(command, finalList);
        
        command = "edit 8 n/Meet KappaRoss p/veryhigh r/11weeks d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        assertEditSuccess(command, finalList);
    }
    
    @Test
    public void editCommand_invalidIndex_invalidCommand(){
        // Check if program handles invalid index properly with a thrown message
        String command = "edit 99 n/King Arthur's birthday i/This is just another information";
        assertInvalidIndex(command);
        
        commandBox.runCommand("clear");
        
        command = "edit 1 n/King Arthur's birthday i/This is just another information";
        assertInvalidIndex(command);
    }
    
    @Test
    public void editCommand_invalidFormat_invalidEditFormat(){
        // Check if program handles invalid edit format properly with a thrown message
        String command = "edit";
        assertInvalidEditCommandFormat(command);
    }
    
    
    @Test
    public void editCommand_nonDatedTaskNotInSequence_Success(){
        // Check if program is able to edit a non-dated task in the middle of list with tasks not in sequence
        TestTask[] listToEdit = td.getTypicalTasks();
        String command = "edit 3 i/This is also an information p/high";
        assertEditSuccess(command, listToEdit);
    }
   
    @Test
    public void editCommand_datedTaskNotInSequence_Success(){
        // Check if program is able to edit a dated task in the middle of list with tasks not in sequence
        TestTask[] listToEdit = td.getTypicalTasks();
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        TestTask datedTaskToAdd = td.dinnerDate;
        
        String command = "edit 8 n/Meet Isabel p/High r/6d d/01-01-2017 18:00 i/Meet up for CS2101 briefing";
        TestTask[] finalList = TestUtil.addTasksToList(listToEdit, datedTaskToAdd);
        
        assertEditSuccess(command, finalList);
    }
    
    /**
     * Helper methods start here
     */
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