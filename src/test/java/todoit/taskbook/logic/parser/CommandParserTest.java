// @@author A0140155U
package todoit.taskbook.logic.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import todoit.taskbook.commons.exceptions.IllegalValueException;
import todoit.taskbook.logic.parser.CommandParser;
import todoit.taskbook.logic.parser.ParsedCommand;

public class CommandParserTest {

    @Test
    public void commandParser_nullString_noValueNoParams() {
        ParsedCommand command = new CommandParser(null);
        assertNoParams(command);
        assert(!command.hasValue());
    }
    
    @Test
    public void commandParser_emptyString_noValueNoParams() {
        ParsedCommand command = new CommandParser("");
        assertNoParams(command);
        assert(!command.hasValue());
    }
    
    @Test
    public void commandParser_oneValue_oneValueNoParams() {
        ParsedCommand command = new CommandParser("value");
        assertNoParams(command);
        assert(command.hasValue());
        assertValue(command, 0, "value");
    }
    
    @Test
    public void commandParser_manyValues_threeValuesNoParams() {
        ParsedCommand command = new CommandParser("value1 value2 value3");
        assertNoParams(command);
        assertValue(command, 0, "value1");
        assertValue(command, 1, "value2");
        assertValue(command, 2, "value3");
    }
    
    @Test
    public void getValuesAsString_emptyString_emptyStringReturned() {
        ParsedCommand command = new CommandParser("");
        assertNoParams(command);
        assertEquals(command.getValuesAsString(), "");
    }
    
    @Test
    public void getValuesAsString_oneValue_oneValueReturned() {
        ParsedCommand command = new CommandParser("value1");
        assertNoParams(command);
        assertEquals(command.getValuesAsString(), "value1");
    }
    
    @Test
    public void getValuesAsString_multipleValues_multipleValuesAsStringReturned() {
        ParsedCommand command = new CommandParser("value1 value2 value3");
        assertNoParams(command);
        assertEquals(command.getValuesAsString(), "value1 value2 value3");
    }
    
    @Test
    public void getValues_outOfRangeValue_exceptionThrown() {
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
    public void commandParser_emptyParam_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("param/");
        assert(!command.hasValue());
        assertParam(command, "param", "");
    }
    
    @Test
    public void commandParser_noParamName_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("/paramValue");
        assert(!command.hasValue());
        assertParam(command, "", "paramValue");
    }
    
    @Test
    public void commandParser_oneParam_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("param/paramValue");
        assert(!command.hasValue());
        assertParam(command, "param", "paramValue");
    }

    @Test
    public void commandParser_noParamNameAndValue_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("/");
        assert(!command.hasValue());
        assertParam(command, "", "");
    }
    
    @Test
    public void commandParser_spacedParam_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("param/param value");
        assert(!command.hasValue());
        assertParam(command, "param", "param value");
    }

    @Test
    public void commandParser_multipleParamDelims_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("param/param/value");
        assert(!command.hasValue());
        assertParam(command, "param", "param/value");
    }
    
    @Test
    public void commandParser_leadingSpaceParams_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("param/ value");
        assert(!command.hasValue());
        assertParam(command, "param", " value");
    }
    
    @Test
    public void commandParser_multipleParamValues_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param1/paramValue2");
        assert(!command.hasValue());
        assertParam(command, "param1", "paramValue1");
        assertParamList(command, "param1", "paramValue1", "paramValue2");
    }

    @Test
    public void commandParser_multipleParams_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue2");
        assert(!command.hasValue());
        assertParam(command, "param1", "paramValue1");
        assertParam(command, "param2", "paramValue2");
    }
    
    @Test
    public void commandParser_multipleParamsMultipleParamValues_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue1 param1/paramValue2 param2/paramValue2");
        assert(!command.hasValue());
        assertParamList(command, "param1", "paramValue1", "paramValue2");
        assertParamList(command, "param2", "paramValue1", "paramValue2");
    }
    
    @Test
    public void commandParser_multipleSpacedParams_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("param1/param Value 1 param2/param Value 2");
        assert(!command.hasValue());
        assertParam(command, "param1", "param Value 1");
        assertParam(command, "param2", "param Value 2");
    }
    
    @Test
    public void getParam_invalidParam_exceptionThrown() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue2");
        assert(!command.hasValue());
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
    public void getDefaultParam_invalidParam_defaultParamReturned() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue2");
        assert(!command.hasValue());
        assertEquals(command.getParamOrDefault("param3", "-1"), "-1");
    }

    @Test
    public void getDefaultParam_validParam_paramReturned() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue2");
        assert(!command.hasValue());
        assertEquals(command.getParamOrDefault("param2", "-1"), "paramValue2");
    }
    
    @Test
    public void commandParser_valueAndParam_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("value param/paramValue");
        assertValue(command, 0, "value");
        assertParam(command, "param", "paramValue");
    }

    @Test
    public void commandParser_multipleValuesAndParams_parsedSuccessfully() {
        ParsedCommand command = new CommandParser("value1 value2 param1/param Value 1 param2/param Value 2");
        assertValue(command, 0, "value1");
        assertValue(command, 0, "value1");
        assertValue(command, 1, "value2");
        assertParam(command, "param1", "param Value 1");
        assertParam(command, "param2", "param Value 2");
    }
    
    @Test
    public void getAllParams_multipleParams_allParamsReturned() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue1 param1/paramValue2 param2/paramValue1");
        String[] result = {"param1", "param2"};
        ArrayList<String> paramList = command.getParamList();
        assertEqualValues(paramList, result);
    }

    @Test
    public void getAllValues_multipleValues_allValuesReturned() {
        ParsedCommand command = new CommandParser("value1 value2 value3");
        String[] result = {"value1", "value2", "value3"};
        ArrayList<String> valueList = command.getAllValues();
        assertEqualValues(valueList, result);
    } 

    @Test
    public void hasParams_hasParams_returnsTrue() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue1");
        String[] params = {"param1", "param2"};
        assert(command.hasParams(params));
    } 

    @Test
    public void hasParams_doesNotHaveParams_returnsFalse() {
        ParsedCommand command = new CommandParser("param1/paramValue1 param2/paramValue1");
        String[] params2 = {"param1", "param2", "param3"};
        assert(!command.hasParams(params2));
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
        ArrayList<String> paramList = command.getAllParams(param);
        assertEqualValues(paramList, paramValues);
    }

    private void assertEqualValues(ArrayList<String> paramList, String[] paramValues){
        for(int i = 0; i < paramValues.length; i++){
            assertEquals(paramValues[i], paramList.get(i));
        }
    }

    private void assertNoParams(ParsedCommand command){
        assert(command.getParamList().size() == 0);
    }
}
//@@author