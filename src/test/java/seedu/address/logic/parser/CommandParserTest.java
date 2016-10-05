package seedu.address.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class CommandParserTest {

    @Test
    public void nullCommand() {
        ParsedCommand command = new CommandParser(null);
        assertEquals(command.getCommandName(), "");
        assertNoParams(command);
        assertNoValues(command);
    }
    
    @Test
    public void simpleCommand() {
        ParsedCommand command = new CommandParser("test");
        assertEquals(command.getCommandName(), "test");
    }
    
    @Test
    public void oneValue() {
        ParsedCommand command = new CommandParser("test value");
        assertNoParams(command);
        assertValue(command, 0, "value");
    }
    
    @Test
    public void multipleValues() {
        ParsedCommand command = new CommandParser("test value1 value2 value3");
        assertValue(command, 0, "value1");
        assertValue(command, 0, "value1");
        assertValue(command, 1, "value2");
        assertValue(command, 2, "value3");
    }
    
    @Test
    public void oneParamNoValue() {
        ParsedCommand command = new CommandParser("test param/");
        assertNoValues(command);
        assertParam(command, "param", "");
    }
    
    @Test
    public void oneParamNoName() {
        ParsedCommand command = new CommandParser("test /paramValue");
        assertParam(command, "", "paramValue");
    }
    
    @Test
    public void oneParam() {
        ParsedCommand command = new CommandParser("test param/paramValue");
        assertParam(command, "param", "paramValue");
    }

    @Test
    public void emptyParam() {
        ParsedCommand command = new CommandParser("test /");
        assertParam(command, "", "");
    }
    
    @Test
    public void spacedParam() {
        ParsedCommand command = new CommandParser("test param/param value");
        assertParam(command, "param", "param value");
    }

    @Test
    public void multipleParamDelimiters() {
        ParsedCommand command = new CommandParser("test param/param/value");
        assertParam(command, "param", "param/value");
    }
    
    @Test
    public void leadingSpaceParam() {
        ParsedCommand command = new CommandParser("test param/ value");
        assertParam(command, "param", " value");
    }
    
    @Test
    public void sameParams() {
        ParsedCommand command = new CommandParser("test param1/paramValue1 param1/paramValue2");
        StringBuilder sb = new StringBuilder();
        
        assertParam(command, "param1", "param Value 1");
        assertParam(command, "param2", "param Value 2");
    }
    
    @Test
    public void multipleParams() {
        ParsedCommand command = new CommandParser("test param1/paramValue1 param2/paramValue2");
        assertParam(command, "param1", "paramValue1");
        assertParam(command, "param2", "paramValue2");
    }

    @Test
    public void multipleSpacedParams() {
        ParsedCommand command = new CommandParser("test param1/param Value 1 param2/param Value 2");
        assertParam(command, "param1", "param Value 1");
        assertParam(command, "param2", "param Value 2");
    }
    
    @Test
    public void valueAndParam() {
        ParsedCommand command = new CommandParser("test value param/paramValue");
        assertValue(command, 0, "value");
        assertParam(command, "param", "paramValue");
    }

    @Test
    public void multipleValuesAndParams() {
        ParsedCommand command = new CommandParser("test value1 value2 param1/param Value 1 param2/param Value 2");
        assertValue(command, 0, "value1");
        assertValue(command, 0, "value1");
        assertValue(command, 1, "value2");
        assertParam(command, "param1", "param Value 1");
        assertParam(command, "param2", "param Value 2");
    }
    
    private void assertValue(ParsedCommand command, int index, String value){
        try {
            assertEquals(command.getValue(index), value);
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }
    
    private void assertParam(ParsedCommand command, String param, String paramValue){
        try {
            assertEquals(command.getParam(param), paramValue);
        } catch (IllegalValueException e) {
            fail(e.getMessage());
        }
    }
    
    private void assertNoValues(ParsedCommand command){
        assert(command.getAllValues().size() == 0);
    }

    private void assertNoParams(ParsedCommand command){
        assert(command.getAllParams().size() == 0);
    }
}
