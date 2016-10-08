package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask aliceMeeting, breadShopping, lorryMaintainance, danielLunch, fetchElle, 
                           researchPaper, mumLunch, neiceBirhdayMeal, surveyResults;

    public TypicalTestTasks() {
        try {
            aliceMeeting =  new TaskBuilder().withName("Meeting with Alice Pauline").withPriority("medium")
                    .withInformation("To pitch business idea").withDoneFlag("Not done")
                    .withTags("boss").build();
            breadShopping = new TaskBuilder().withName("Buy 2 packet of bread").withPriority("High")
                    .withInformation("On shopping trip wth friends").withDoneFlag("Not done")
                    .withTags("owesMoney", "friends").build();
            lorryMaintainance = new TaskBuilder().withName("Send in lorry for maintainance").withDoneFlag("Done").withInformation("Take 10 month interest loan").withPriority("medium").build();
            danielLunch = new TaskBuilder().withName("Lunch with Daniel Meier").withDoneFlag("Not done").withInformation("Catch up").withPriority("medium").build();
            fetchElle = new TaskBuilder().withName("Fetch Elle Meyer from airport").withDoneFlag("Not done").withInformation("Returning flight yet to confirm").withPriority("low").build();
            researchPaper = new TaskBuilder().withName("Finish off research paper").withDoneFlag("Not done").withInformation("Missing appendix A and B").withPriority("veryHigh").build();
            mumLunch = new TaskBuilder().withName("Treat mum to lunch").withDoneFlag("Not done").withInformation("To celebrate retirement.").withPriority("VeryHigh").build();

            //Manually added
            neiceBirhdayMeal = new TaskBuilder().withName("Bring neice out for a meal").withDoneFlag("Done").withInformation("Belated birthday treat").withPriority("High").build();
            surveyResults = new TaskBuilder().withName("Collate customer survey results").withDoneFlag("Not done").withInformation("As soon as possible").withPriority("VeryHigh").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

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

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{aliceMeeting, breadShopping, lorryMaintainance, 
                                danielLunch, fetchElle,researchPaper, mumLunch};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
