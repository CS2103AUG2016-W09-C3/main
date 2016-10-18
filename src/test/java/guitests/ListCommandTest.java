package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DoneFlag;
import seedu.address.testutil.TestDatedTask;
import seedu.address.testutil.TestTask;

public class ListCommandTest extends AddressBookGuiTest{
    
    @Test
    public void list_allTask(){
        TestTask[] allTaskList = td.getTypicalPersons();
        String command = "list df/all";
        commandBox.runCommand(command);
        assertTrue(personListPanel.isListMatching(allTaskList));
    }
    
    @Test
    public void list_allTask_reverse(){
        TestTask[] initialList = td.getTypicalPersons();
        String command = "list df/all rev/";
        TestTask[] finalList = new TestTask[initialList.length];
        for(int i= 0; i < initialList.length; i++){
            finalList[initialList.length - 1 - i] = initialList[i];
        }
        commandBox.runCommand(command);
        assertTrue(personListPanel.isListMatching(finalList));
    }
    
    @Test
    public void list_doneTask(){
        TestTask[] initialList = td.getTypicalPersons();
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
        assertTrue(personListPanel.isListMatching(finalList));
    }
    
    @Test
    public void list_undoneTask(){
        TestTask[] initialList = td.getTypicalPersons();
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
        assertTrue(personListPanel.isListMatching(finalList));
    }
    
    @Test
    public void list_allTask_alphabetical(){
        TestTask[] initialList = td.getTypicalPersons();
        TestTask[] finalList = new TestTask[initialList.length];
        finalList[0] = initialList[1];
        finalList[1] = initialList[4];
        finalList[2] = initialList[5];
        finalList[3] = initialList[3];
        finalList[4] = initialList[0];
        finalList[5] = initialList[2];
        finalList[6] = initialList[6];
        String command = "list df/all s/name";
        commandBox.runCommand(command);
        assertTrue(personListPanel.isListMatching(finalList));
    }
    
    @Test
    public void list_undoneTask_priority(){
        TestTask[] initialList = td.getTypicalPersons();
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
        assertTrue(personListPanel.isListMatching(finalList));
    }
    
    @Test
    public void list_doneTask_daterange_none(){
        //TestDatedTask[] initialList = (TestDatedTask[]) td.getTypicalPersons();
        commandBox.runCommand(td.lectureToAttend.getAddCommand());
        commandBox.runCommand(td.meetNathan.getAddCommand());
        commandBox.runCommand(td.cuttingHair.getAddCommand());
        commandBox.runCommand("done 8");
        String command = "list df/done ds/10-11-2016 de/25-11-2016";
        TestDatedTask[] finalList = new TestDatedTask[0];
        commandBox.runCommand(command);
        assertTrue(personListPanel.isListMatching(finalList));
    }
    
    @Test
    public void list_doneTask_daterange_one(){
        //TestDatedTask[] initialList = (TestDatedTask[]) td.getTypicalPersons();
        commandBox.runCommand(td.lectureToAttend.getAddCommand());
        commandBox.runCommand(td.meetNathan.getAddCommand());
        commandBox.runCommand(td.cuttingHair.getAddCommand());
        commandBox.runCommand("done 8");
        //boundary value test
        String command = "list df/done ds/10-10-2016 de/25-11-2016";
        TestDatedTask[] finalList = new TestDatedTask[1];
        finalList[0] = td.lectureToAttend;
        try {
            finalList[0].setDoneFlag(new DoneFlag(DoneFlag.DONE));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        commandBox.runCommand(command);
        assertTrue(personListPanel.isListMatching(finalList));
    }
}
