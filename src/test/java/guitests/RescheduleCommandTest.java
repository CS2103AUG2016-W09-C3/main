//@@author A0139046E
package guitests;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RescheduleCommand;
import seedu.address.model.task.DateParser;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.TimeInterval;
import seedu.address.testutil.TestDatedTask;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class RescheduleCommandTest extends AddressBookGuiTest {
	
	@Test
	public void reschedule_minutes(){
		commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        commandBox.runCommand(td.meetingToAttend.getAddCommand());
		TestTask[] initialList = td.getTypicalTasks();
        TestTask datedTaskToAdd = td.dinnerDate;
        TestTask datedTaskToAdd2 = td.csFinalExam;
        TestTask datedTaskToAdd3 = td.meetingToAttend;
        TestTask[] finalList = TestUtil.addTasksToList(initialList, datedTaskToAdd, datedTaskToAdd2, datedTaskToAdd3);
        String command = "reschedule 10 30m";
        assertRescheduleSuccess(command, finalList);
	}
	
	@Test
	public void reschedule_days(){
		commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        commandBox.runCommand(td.meetingToAttend.getAddCommand());
		TestTask[] initialList = td.getTypicalTasks();
        TestTask datedTaskToAdd = td.dinnerDate;
        TestTask datedTaskToAdd2 = td.csFinalExam;
        TestTask datedTaskToAdd3 = td.meetingToAttend;
        TestTask[] finalList = TestUtil.addTasksToList(initialList, datedTaskToAdd, datedTaskToAdd2, datedTaskToAdd3);
        String command = "reschedule 9 2d";
        assertRescheduleSuccess(command, finalList);
	}
	
	@Test
	public void reschedule_week(){
		commandBox.runCommand(td.dinnerDate.getAddCommand());
        commandBox.runCommand(td.csFinalExam.getAddCommand());
        commandBox.runCommand(td.meetingToAttend.getAddCommand());
		TestTask[] initialList = td.getTypicalTasks();
        TestTask datedTaskToAdd = td.dinnerDate;
        TestTask datedTaskToAdd2 = td.csFinalExam;
        TestTask datedTaskToAdd3 = td.meetingToAttend;
        TestTask[] finalList = TestUtil.addTasksToList(initialList, datedTaskToAdd, datedTaskToAdd2, datedTaskToAdd3);
        String command = "reschedule 8 2w";
        assertRescheduleSuccess(command, finalList);
	}
	
	@Test 
	public void reschedule_notDatedTask(){
		commandBox.runCommand(td.dinnerDate.getAddCommand());
		String command = "reschedule 1 5d";
		commandBox.runCommand(command);
		assertResultMessage(String.format(RescheduleCommand.MESSAGE_TASK_NOT_DATED));
	}
	
	@Test 
	public void reschedule_invalidCommandFormat(){
		 commandBox.runCommand(td.csFinalExam.getAddCommand());
	     commandBox.runCommand(td.meetingToAttend.getAddCommand());
	     String command = "reschedule";
	     commandBox.runCommand(command);
	     assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RescheduleCommand.MESSAGE_USAGE));
	}
	
	@Test
	public void reschedule_invalidTimeInterval(){
		commandBox.runCommand(td.csFinalExam.getAddCommand());
	    commandBox.runCommand(td.meetingToAttend.getAddCommand());
	    String command = "reschedule 8 10dd";
	    commandBox.runCommand(command);
	    assertResultMessage(String.format(TimeInterval.MESSAGE_TIME_INTERVAL_CONSTRAINTS));
	}
	
	@Test 
	public void reschedule_invalidIndex(){
		commandBox.runCommand(td.csFinalExam.getAddCommand());
	    String command = "reschedule 10 20d";
	    commandBox.runCommand(command);
	    assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
	}
	
	private void assertRescheduleSuccess(String command, TestTask[] finalList){
		String[] split = command.split("\\s+");
        int targetIndex = Integer.parseInt(split[1]);
        String interval = split[2];
		TestTask[] expected = rescheduleTask(targetIndex, interval, finalList);
		commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(expected));
        assertResultMessage(String.format(RescheduleCommand.MESSAGE_RESCHEDULE_TASK_SUCCESS, expected[targetIndex -1]));
	}
	
	private TestTask[] rescheduleTask(int targetIndex, String interval, TestTask[] finalList){
		try {
			System.out.println(interval);
			TestTask target = finalList[targetIndex - 1];
			TestTask[] remainderList = TestUtil.removeTaskFromList(finalList, targetIndex);
			
			LocalDateTime rescheduleDatetime = ((TestDatedTask) target).getDateTime().datetime;
			System.out.println(rescheduleDatetime.toString());
			LocalDateTime newDateTime = DateParser.rescheduleDate(rescheduleDatetime, interval);
			System.out.println(newDateTime.toString());
			((TestDatedTask) target).setDateTimeString(newDateTime.toString());
			
			remainderList = TestUtil.addTaskToListIndex(remainderList, target, targetIndex - 1);
		} catch (IllegalValueException e) {
			assert(false);
		}
		
		return finalList;
	}
}
//@@author