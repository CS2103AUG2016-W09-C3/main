package seedu.address.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class CommandParserTest {

    @Test
    public void nullCommand() {
        ParsedCommand command = new CommandParser(null);
        assertEquals(command.getCommandName(), "");
    }
    
    @Test
    public void simpleCommand() {
        ParsedCommand command = new CommandParser("test");
        assertEquals(command.getCommandName(), "test");
    }
    
    @Test
    public void oneValue() {
        ParsedCommand command = new CommandParser("test value");
        try {
            assertEquals(command.getValue(), "value");
        } catch (IllegalValueException e) {
            fail("Cannot get 1 value.");
        }
    }
    
    @Test
    public void multipleValues() {
        ParsedCommand command = new CommandParser("test value1 value2 value3");
        try {
            assertEquals(command.getValue(), "value");
            assertEquals(command.getValue(0), "value1");
            assertEquals(command.getValue(1), "value2");
            assertEquals(command.getValue(2), "value3");
        } catch (IllegalValueException e) {
            fail("Cannot get 1 value.");
        }
    }
    
    @Test
    public void emptyValues() {
        ParsedCommand command = new CommandParser("test   value");
        try {
            assertEquals(command.getValue(), "value");
        } catch (IllegalValueException e) {
            fail("Cannot get 1 value.");
        }
    }
    
    
    @Test
    public void oneParamNoValue() {
        ParsedCommand command = new CommandParser("test param/");
        try {
            checkNoValues(command);
            assertEquals(command.getParam("param"), "");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void oneParamNoName() {
        ParsedCommand command = new CommandParser("test /paramValue");
        try {
            checkNoValues(command);
            assertEquals(command.getParam(""), "paramValue");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void oneParam() {
        ParsedCommand command = new CommandParser("test param/paramValue");
        try {
            checkNoValues(command);
            assertEquals(command.getParam("param"), "paramValue");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void emptyParam() {
        ParsedCommand command = new CommandParser("test /");
        try {
            checkNoValues(command);
            assertEquals(command.getParam(""), "");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void spacedParam() {
        ParsedCommand command = new CommandParser("test param/param value");
        try {
            checkNoValues(command);
            assertEquals(command.getParam("param"), "param value");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void multipleParamDelimiters() {
        ParsedCommand command = new CommandParser("test param/param/value");
        try {
            checkNoValues(command);
            assertEquals(command.getParam("param"), "param/value");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void leadingSpaceParam() {
        ParsedCommand command = new CommandParser("test param/ value");
        try {
            checkNoValues(command);
            assertEquals(command.getParam("param"), " value");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }
    
    
    @Test
    public void multipleParams() {
        ParsedCommand command = new CommandParser("test param1/paramValue1 param2/paramValue2");
        try {
            checkNoValues(command);
            assertEquals(command.getParam("param1"), "paramValue1");
            assertEquals(command.getParam("param2"), "paramValue2");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void multipleSpacedParams() {
        ParsedCommand command = new CommandParser("test param1/param Value 1 param2/param Value 2");
        try {
            checkNoValues(command);
            assertEquals(command.getParam("param1"), "param Value 1");
            assertEquals(command.getParam("param2"), "param Value 2");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }
    

    @Test
    public void valueAndParam() {
        ParsedCommand command = new CommandParser("test value param/paramValue");
        try {
            assertEquals(command.getValue(), "value");
            assertEquals(command.getParam("param"), "paramValue");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void multipleValuseAndParams() {
        ParsedCommand command = new CommandParser("test value1 value2 param1/param Value 1 param2/param Value 2");
        try {
            assertEquals(command.getValue(), "value1");
            assertEquals(command.getValue(0), "value1");
            assertEquals(command.getValue(1), "value2");
            assertEquals(command.getParam("param1"), "param Value 1");
            assertEquals(command.getParam("param2"), "param Value 2");
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }
    
    private void checkNoValues(ParsedCommand command){
        try{
            command.getValue();
            fail("Command contained values, expected none.");
        }catch(IllegalValueException ex){
            assertEquals(ex.getMessage(), String.format(CommandParser.VALUE_OUT_OF_BOUNDS_MESSAGE, 0));
        }
    }
}
