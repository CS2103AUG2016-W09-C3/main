package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask aliceMeeting, breadShopping, lorryMaintainance, danielLunch, fetchElle, 
                           researchPaper, mumLunch, nieceBirthdayMeal, surveyResults;
    
    public static TestDatedTask lectureToAttend, meetNathan, cuttingHair, dinnerDate, meetingToAttend, csFinalExam;

    public TypicalTestTasks() {
        try {
            aliceMeeting =  new TaskBuilder().withName("Meeting with Alice Pauline").withPriority("medium")
                    .withInformation("To pitch business idea").withDoneFlag("Not done")
                    .withTags("boss").build();
            
            breadShopping = new TaskBuilder().withName("Buy 2 packet of bread").withPriority("High")
                    .withInformation("On shopping trip wth meier").withDoneFlag("Not done")
                    .withTags("owesMoney", "friends").build();
            
            lorryMaintainance = new TaskBuilder().withName("Send in lorry for maintainance").withDoneFlag("Not done")
                    .withInformation("Take 10 month interest loan").withPriority("medium").build();
            
            danielLunch = new TaskBuilder().withName("Lunch with Daniel Meier").withDoneFlag("Not done")
                    .withInformation("Catch up").withPriority("medium").build();
            
            fetchElle = new TaskBuilder().withName("Fetch Elle Meyer from airport").withDoneFlag("Not done")
                    .withInformation("Returning flight yet to confirm").withPriority("low").build();
            
            researchPaper = new TaskBuilder().withName("Finish off research paper").withDoneFlag("Not done")
                    .withInformation("Missing appendix A and B").withPriority("veryHigh").build();
            
            mumLunch = new TaskBuilder().withName("Treat mum to lunch").withDoneFlag("Not done")
                    .withInformation("To celebrate retirement.").withPriority("VeryHigh").build();

            //Manually added (Not dated)
            nieceBirthdayMeal = new TaskBuilder().withName("Bring neice out for a meal").withDoneFlag("Not done").
                    withInformation("Belated birthday treat").withPriority("High").build();
            
            surveyResults = new TaskBuilder().withName("Collate customer survey results").withDoneFlag("Not done").
                    withInformation("As soon as possible").withPriority("VeryHigh").build();
            
            //Manually added (dated)
            lectureToAttend = new DatedTaskBuilder().withName("Attend CS2103T lecture").withDoneFlag("Not done").withDateTime("10-10-2016 1400")
                    .withLength("2h").withRecurrance("5d").withPriority("low").withInformation("There is webcast if you do not attend").build();
            
            meetNathan = new DatedTaskBuilder().withName("Meet Nathan for dinner").withDoneFlag("Not done").withDateTime("11-25-2016 1800")
                    .withLength("3h").withRecurrance("3d").withPriority("medium").withInformation("Meeting nathan to catch up").build();
            
            cuttingHair = new DatedTaskBuilder().withName("Cut hair").withDoneFlag("Not done").withDateTime("11-10-2016 0900")
                    .withLength("1h").withRecurrance("6d").withPriority("medium").withInformation("Cut hair so your hair will not be long").build();
            
            dinnerDate = new DatedTaskBuilder().withName("Dinner date").withDoneFlag("Not done").withDateTime("11-17-2016 1900")
                    .withLength("2h").withRecurrance("5d").withPriority("medium").withInformation("Nice dinner at restuarant").build();
            
            meetingToAttend = new DatedTaskBuilder().withName("Meeting with CS2101 tutor").withDoneFlag("Not done").withDateTime("10-30-2016 1000")
                    .withLength("2h").withRecurrance("3d").withPriority("high").withInformation("At COM2").build();
            
            csFinalExam = new DatedTaskBuilder().withName("CS2103T Finals").withDoneFlag("Not done").withDateTime("11-28-2016 1800")
                    .withLength("2h").withRecurrance("1d").withPriority("high").withInformation("At MPSH").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new Task(aliceMeeting));
            ab.addTask(new Task(breadShopping));
            ab.addTask(new Task(lorryMaintainance));
            ab.addTask(new Task(danielLunch));
            ab.addTask(new Task(fetchElle));
            ab.addTask(new Task(researchPaper));
            ab.addTask(new Task(mumLunch));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] {aliceMeeting, breadShopping, lorryMaintainance, 
                                danielLunch, fetchElle, researchPaper, mumLunch};
    }

    public TestTask[] getTypicalDatedTask() {
        return new TestTask[] {lectureToAttend, meetNathan, cuttingHair, dinnerDate, meetingToAttend, csFinalExam};
    }

    public TaskBook getTypicalAddressBook(){
        TaskBook ab = new TaskBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
