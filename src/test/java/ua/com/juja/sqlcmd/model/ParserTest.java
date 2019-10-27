package ua.com.juja.sqlcmd.model;

import org.junit.Assert;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.CommandParser;

import java.util.List;

public class ParserTest {
    private CommandParser parser = new CommandParser();

    @Test
    public void testIsGetAllParameters() {
        String input = "insert|teachers|id|2|surname|Kish";
        List<String> allParameters = parser.getParameters(input);
        Assert.assertEquals("[insert, teachers, id, 2, surname, Kish]", allParameters.toString());
    }

    @Test
    public void isCorrectlyGetCommandName(){
        String input = "connect|Academy|postgres|14011998n";
        String commandName = parser.getCommandName(input);
        Assert.assertEquals("connect", commandName);
    }

    @Test
    public void isCorrectlyGetTable(){
        String input = "insert|teachers";
        String tableName = parser.getTableName(input);
        Assert.assertEquals("teachers", tableName);
    }

    @Test
    public void isCorrectlyGetColumns(){
        String input = "insert|teachers|id|2|surname|Kish";
        List<String> columns = parser.getColumns(input);
        Assert.assertEquals("[id, surname]", columns.toString());
    }

    @Test
    public void isCorrectlyGetRows(){
        String input = "insert|teachers|id|2|surname|Kish";
        List<String> columns = parser.getRows(input);
        Assert.assertEquals("[2, Kish]", columns.toString());
    }
}
