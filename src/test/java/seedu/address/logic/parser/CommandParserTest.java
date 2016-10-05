package seedu.address.logic.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class CommandParserTest {

    @Test
    public void nullCommand() {
        ParsedCommand command = new CommandParser(null);
        assertNoParams(command);
        assertNoValues(command);
    }
    
    @Test
    public void noExtraArgsCommand() {
        ParsedCommand command = new CommandParser("");
        assertNoParams(command);
        assertNoValues(command);
    }
    
    @Test
    public void oneValue() {
        ParsedCommand command = new CommandParser("value");
        assertNoParams(command);
        assertValue(command, 0, "value");
    }
    
    @Test
    public void multipleValues() {
        ParsedCommand command = new CommandParser("value1 value2 value3");
        assertValue(command, 0, "value1");
        assertValue(command, 0, "value1");
        assertValue(command, 1, "value2");
        assertValue(command, 2, "value3");
    }
    
    @Test
    public void noValuesAsString() {
        ParsedCommand command = new CommandParser("");
        assertEquals(command.getValuesAsString(), "");
    }
    
    @Test
    public void oneValueAsString() {
        ParsedCommand command = new CommandParser("value1");
        assertEquals(command.getValuesAsString(), "value1");
    }
    
    @Test
    public void multipleValuesAsString() {
        ParsedCommand command = new CommandParser("value1 value2 value3");
        assertEquals(command.getValuesAsString(), "value1 value2 value3");
    }
    
    @Test
    public void valueOutOfRange() {
        ParsedCommand command = new CommandParser("value1 value2 value3");
        int outOfBoundsIndex = 3;
        String errMsg = String.format(CommandParser.VALUE_OUT_OF_BOUNDS_MESSAGE, outOfBoundsIndex);
        try {
            command.getValue(outOfBoundsIndex);
            fail("Expected " + errMsg);
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), errMsg);
        }
    }
    
    
    @Test
    public void oneParamNoValue() {
        ParsedCommand command = new CommandParser("param/");
        assertNoValues(command);
        assertParam(command, "param", "");
    }
    
    @Test
    public void oneParamNoName() {
        ParsedCommand command = new CommandParser("/paramValue");
        assertParam(command, "", "paramValue");
    }
    
    @Test
    public void oneParam() {
        ParsedCommand command = new CommandParser("param/paramValue");
        assertParam(command, "param", "paramValue");
    }

    @Test
    public void emptyParam() {
        ParsedCommand command = new CommandParser("/");
        assertParam(command, "", "");
    }
    
    @Test
    public void spacedParam() {
        ParsedCommand command = new CommandParser("param/param value");
        assertParam(command, "param", "param value");
    }

    @Test
    public void multipleParamDelimiters() {
        ParsedCommand command = new CommandParser("param/param/value");
        assertParam(command, "param", "param/value");
    }
    
    @Test
    public void leadingSpaceParam() {
        ParsedCommand command = new CommandParser("param/ value");
        assertParam(command, "param", " value");
    }
    
    @Test
    public void sameParams() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param1/paramValue2");
        assertParam(command, "param1", "paramValue1");
        assertParamList(command, "param1", "paramValue1", "paramValue2");
    }
    
    @Test
    public void emptySameParams() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param1/paramValue2");
        assertParamList(command, "param2");
    }
    
    @Test
    public void mixedSameParams() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue1 param1/paramValue2 param2/paramValue2");
        assertParamList(command, "param1", "paramValue1", "paramValue2");
        assertParamList(command, "param2", "paramValue1", "paramValue2");
    }
    
    @Test
    public void multipleParams() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue2");
        assertParam(command, "param1", "paramValue1");
        assertParam(command, "param2", "paramValue2");
    }

    @Test
    public void multipleSpacedParams() {
        ParsedCommand command = new CommandParser("param1/param Value 1 param2/param Value 2");
        assertParam(command, "param1", "param Value 1");
        assertParam(command, "param2", "param Value 2");
    }
    
    @Test
    public void invalidParam() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue2");
        String invalidParam = "param3";
        String errMsg = String.format(CommandParser.NO_PARAM_MESSAGE, invalidParam);
        try {
            command.getParam(invalidParam);
            fail("Expected " + errMsg);
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), errMsg);
        }
    }
    
    @Test
    public void getDefaultParam() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue2");
        assertEquals(command.getParamOrDefault("param3", "-1"), "-1");
    }

    @Test
    public void ignoreDefaultParam() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue2");
        assertEquals(command.getParamOrDefault("param2", "-1"), "paramValue2");
    }
    
    @Test
    public void valueAndParam() {
        ParsedCommand command = new CommandParser("value param/paramValue");
        assertValue(command, 0, "value");
        assertParam(command, "param", "paramValue");
    }

    @Test
    public void multipleValuesAndParams() {
        ParsedCommand command = new CommandParser("value1 value2 param1/param Value 1 param2/param Value 2");
        assertValue(command, 0, "value1");
        assertValue(command, 0, "value1");
        assertValue(command, 1, "value2");
        assertParam(command, "param1", "param Value 1");
        assertParam(command, "param2", "param Value 2");
    }
    
    @Test
    public void getAllParams() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue1 param1/paramValue2 param2/paramValue1");
        String[] result = {"param1", "param2"};
        ArrayList<String> paramList = command.getAllParams();
        assertEqualValues(paramList, result);
    }

    @Test
    public void getAllValues() {
        ParsedCommand command = new CommandParser("value1 value2 value3");
        String[] result = {"value1", "value2", "value3"};
        ArrayList<String> valueList = command.getAllValues();
        assertEqualValues(valueList, result);
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
    
    private void assertParamList(ParsedCommand command, String param, String... paramValues){
        ArrayList<String> paramList = command.getParamList(param);
        assertEqualValues(paramList, paramValues);
    }

    private void assertEqualValues(ArrayList<String> paramList, String[] paramValues){
        for(int i = 0; i < paramValues.length; i++){
            assertEquals(paramValues[i], paramList.get(i));
        }
    }

    private void assertNoValues(ParsedCommand command){
        assert(command.getAllValues().size() == 0);
    }

    private void assertNoParams(ParsedCommand command){
        assert(command.getAllParams().size() == 0);
    }
}
