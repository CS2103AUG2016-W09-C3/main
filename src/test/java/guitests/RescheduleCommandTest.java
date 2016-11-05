//@@author A0139046E
package guitests;

import static org.junit.Assert.assertTrue;
import java.time.LocalDateTime;
import org.junit.Test;

import todoit.taskbook.commons.core.Messages;
import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.logic.commands.RescheduleCommand;
import todoit.taskbook.model.task.DateParser;
import todoit.taskbook.model.task.TimeInterval;
import todoit.taskbook.testutil.TestDatedTask;
import todoit.taskbook.testutil.TestTask;
import todoit.taskbook.testutil.TestUtil;

public class RescheduleCommandTest extends TaskBookGuiTest {

	@Test
	public void reschedule_minutes_rescheduledSuccess() {
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
	public void reschedule_hours_rescheduledSuccess() {
		commandBox.runCommand(td.dinnerDate.getAddCommand());
		commandBox.runCommand(td.csFinalExam.getAddCommand());
		TestTask[] initialList = td.getTypicalTasks();
		TestTask datedTaskToAdd = td.dinnerDate;
		TestTask datedTaskToAdd2 = td.csFinalExam;
		TestTask[] finalList = TestUtil.addTasksToList(initialList, datedTaskToAdd, datedTaskToAdd2);
		String command = "reschedule 8 2h";
		assertRescheduleSuccess(command, finalList);
	}

	@Test
	public void reschedule_days_rescheduledSuccess() {
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
	public void reschedule_week_rescheduledSuccess() {
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
	public void reschedule_minutesDifferentAliases_rescheduledSuccess() {
		commandBox.runCommand(td.dinnerDate.getAddCommand());
		commandBox.runCommand(td.csFinalExam.getAddCommand());
		TestTask[] initialList = td.getTypicalTasks();
		TestTask datedTaskToAdd = td.dinnerDate;
		TestTask datedTaskToAdd2 = td.csFinalExam;
		TestTask[] finalList = TestUtil.addTasksToList(initialList, datedTaskToAdd, datedTaskToAdd2);
		String command = "reschedule 8 30mins";
		assertRescheduleSuccess(command, finalList);
	}

	@Test
	public void reschedule_nonDatedTask_displayNotDatedTaskMsg() {
		commandBox.runCommand(td.nieceBirthdayMeal.getAddCommand());
		String command = "reschedule 1 5d";
		commandBox.runCommand(command);
		assertResultMessage(String.format(RescheduleCommand.MESSAGE_TASK_NOT_DATED));
	}

	@Test
	public void reschedule_invalidCommandFormat_displayInvalidCommandMsg() {
		commandBox.runCommand(td.csFinalExam.getAddCommand());
		String command = "reschedule";
		commandBox.runCommand(command);
		assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RescheduleCommand.MESSAGE_USAGE));
	}

	@Test
	public void reschedule_invalidTimeInterval_displayInvalidTimeIntervalMsg() {
		commandBox.runCommand(td.csFinalExam.getAddCommand());
		commandBox.runCommand(td.meetingToAttend.getAddCommand());
		String command = "reschedule 8 10dd";
		commandBox.runCommand(command);
		assertResultMessage(String.format(TimeInterval.MESSAGE_TIME_INTERVAL_CONSTRAINTS));
	}

	@Test
	public void reschedule_invalidIndex_displayInvalidIndexMsg() {
		commandBox.runCommand(td.csFinalExam.getAddCommand());
		String command = "reschedule 10 20d";
		commandBox.runCommand(command);
		assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
	}

	private void assertRescheduleSuccess(String command, TestTask[] finalList) {
		String[] split = command.split("\\s+");
		int targetIndex = Integer.parseInt(split[1]);
		String interval = split[2];
		TestTask[] expected = rescheduleTask(targetIndex, interval, finalList);
		commandBox.runCommand(command);
		assertTrue(taskListPanel.isListMatching(expected));
		assertResultMessage(
				String.format(RescheduleCommand.MESSAGE_RESCHEDULE_TASK_SUCCESS, expected[targetIndex - 1]));
	}

	private TestTask[] rescheduleTask(int targetIndex, String interval, TestTask[] finalList) {
		try {
			TestTask target = finalList[targetIndex - 1];
			TestTask[] remainderList = TestUtil.removeTaskFromList(finalList, targetIndex);

			LocalDateTime rescheduleDatetime = ((TestDatedTask) target).getDateTime().getDateTime();
			LocalDateTime newDateTime = DateParser.rescheduleDate(rescheduleDatetime, interval);
			((TestDatedTask) target).setDateTimeString(newDateTime.toString());

			remainderList = TestUtil.addTaskToListIndex(remainderList, target, targetIndex - 1);
		} catch (IllegalValueException e) {
			assert (false);
		}

		return finalList;
	}
}
// @@author