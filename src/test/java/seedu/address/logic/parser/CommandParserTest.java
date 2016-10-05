package seedu.address.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class CommandParserTest {

    @Test
    public void test() {
        ParsedCommand command = new CommandParser("");
        try{
            command.getCommandName();
        }catch(IllegalValueException ex){
            assertEquals(ex.getMessage(), CommandParser.NO_COMMAND_NAME_MESSAGE);
        }
    }

}
