//@@author A0139121R
package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.logic.commands.ListCommand;
import todoit.taskbook.model.task.DoneFlag;
import todoit.taskbook.testutil.TestDatedTask;
import todoit.taskbook.testutil.TestTask;

public class ListCommandTest extends TaskBookGuiTest{
    
    //Invalid sort attribute
    @Test
    public void list_invalidSortAttribute_displayErrorMessage(){
        String command = "list s/age";
        commandBox.runCommand(command);
        assertResultMessage(ListCommand.SORT_MESSAGE_USAGE);
    }
    //Test to list all tasks.
    @Test
    public void list_allDoneAndUndoneTasks_listsAllTasks(){
        TestTask[] allTaskList = td.getTypicalTasks();
        String command = "list df/all";
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(allTaskList));
    }
    
    //Test to list all tasks in reverse order.
    @Test
    public void list_allDoneAndUndoneTasksReversed_listsAllTasksReversed(){
        TestTask[] initialList = td.getTypicalTasks();
        String command = "list df/all rev/";
        TestTask[] finalList = new TestTask[initialList.length];
        for(int i= 0; i < initialList.length; i++){
            finalList[initialList.length - 1 - i] = initialList[i];
        }
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    //Test to list only done tasks.
    @Test
    public void list_onlyDoneTasks_listsOnlyDoneTasks(){
        TestTask[] initialList = td.getTypicalTasks();
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("done 3");
        commands.add("done 2");
        commands.add("done 1");
        TestTask[] finalList = new TestTask[3];
        for(int i = 0; i< 3; i++){
            finalList[i] = initialList[i];
        }
        for(int i = 0; i< commands.size(); i++){
            commandBox.runCommand(commands.get(i));
        }
        commandBox.runCommand("list df/done");
        try{
            finalList[0].setDoneFlag(new DoneFlag(DoneFlag.DONE));
            finalList[1].setDoneFlag(new DoneFlag(DoneFlag.DONE));
            finalList[2].setDoneFlag(new DoneFlag(DoneFlag.DONE));
        } catch (IllegalValueException e){
            
        }
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    //Test to list only tasks that are not done.
    @Test
    public void list_onlyUndoneTasks_listsOnlyUndoneTasks(){
        TestTask[] initialList = td.getTypicalTasks();
        ArrayList<String> assistingCommands = new ArrayList<String>();
        assistingCommands.add("done 3");
        assistingCommands.add("done 2");
        assistingCommands.add("done 1");
        String command = "list df/not done";
        TestTask[] finalList = new TestTask[initialList.length - 3];
        for(int i = 3; i< initialList.length; i++){
            finalList[i - 3] = initialList[i];
        }
        for(int i = 0; i< assistingCommands.size(); i++){
            commandBox.runCommand(assistingCommands.get(i));
        }
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    //Test to list all tasks sorted in alphabetical order.
    @Test
    public void list_tasksSortedAlphabetically_listsTasksSortedAlphabetically(){
        TestTask[] initialList = td.getTypicalTasks();
        TestTask[] finalList = new TestTask[initialList.length];
        //manual sorting
        finalList[0] = initialList[1];
        finalList[1] = initialList[4];
        finalList[2] = initialList[5];
        finalList[3] = initialList[3];
        finalList[4] = initialList[0];
        finalList[5] = initialList[2];
        finalList[6] = initialList[6];
        String command = "list df/all s/name";
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    //Test to list all undone tasks, sorted from highest to lowest priority.
    @Test
    public void list_undoneSortedByPriority_listsUndoneSortedByPriority(){
        TestTask[] initialList = td.getTypicalTasks();
        ArrayList<String> assistingCommands = new ArrayList<String>();
        String command = "list df/not done s/priority";
        assistingCommands.add("done 3");
        assistingCommands.add("done 2");
        assistingCommands.add("done 1");
        for(int i = 0; i< assistingCommands.size(); i++){
            commandBox.runCommand(assistingCommands.get(i));
        }
        TestTask[] finalList = new TestTask[initialList.length - 3];
        finalList[0] = initialList[5];
        finalList[1] = initialList[6];
        finalList[2] = initialList[3];
        finalList[3] = initialList[4];
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    //Test to list all done tasks within specific period, should have no done tasks in this period.
    @Test
    public void list_doneTasksWithinDaterange_noTaskInDaterange(){
        commandBox.runCommand(td.lectureToAttend.getAddCommand());
        commandBox.runCommand(td.meetNathan.getAddCommand());
        commandBox.runCommand(td.cuttingHair.getAddCommand());
        commandBox.runCommand("done 8");
        String command = "list df/done ds/11-10-2016 de/11-25-2016";
        TestDatedTask[] finalList = new TestDatedTask[0];
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    //Test to list all done tasks within a specific period, should have one done task within this period.
    @Test
    public void list_doneTasksWithinDaterange_oneTaskInDaterange(){
        commandBox.runCommand(td.lectureToAttend.getAddCommand());
        commandBox.runCommand(td.meetNathan.getAddCommand());
        commandBox.runCommand(td.cuttingHair.getAddCommand());
        commandBox.runCommand("done 8");
        //boundary value test
        String command = "list df/done ds/10-10-2016 de/11-25-2016";
        TestDatedTask[] finalList = new TestDatedTask[1];
        finalList[0] = td.lectureToAttend;
        try {
            finalList[0].setDoneFlag(new DoneFlag(DoneFlag.DONE));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    //Test to list all tasks, in sorted order by date, after start date and time with no end date and time specified.
    @Test
    public void list_daterangeFilterListStartDateOnlySortByDate_twoTasksInDaterange(){
        String command = "list df/all ds/11-10-2016 0900 s/date";
        commandBox.runCommand(td.lectureToAttend.getAddCommand());
        commandBox.runCommand(td.meetNathan.getAddCommand());
        commandBox.runCommand(td.cuttingHair.getAddCommand());
        TestDatedTask[] finalList = {td.cuttingHair, td.meetNathan};
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(finalList));
    }
    
    //Test to list all tasks, in reverse sorted order by date, with only end date and time specified(all task before this end date and time).
    @Test
    public void list_daterangeFilterListEndDateOnlyReverseSortByDate_threeTasksInDaterange(){
        String command = "list df/all de/11-17-2016 1900 s/date rev/";
        commandBox.runCommand(td.lectureToAttend.getAddCommand());
        commandBox.runCommand(td.cuttingHair.getAddCommand());
        commandBox.runCommand(td.meetNathan.getAddCommand());
        commandBox.runCommand(td.dinnerDate.getAddCommand());
        TestDatedTask[] finalList = {td.dinnerDate, td.cuttingHair, td.lectureToAttend};
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(finalList));
    }
}
