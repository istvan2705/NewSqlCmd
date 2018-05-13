package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HelpTest {

    public View view;
    public Command command;

    @Before
    public void init() {
        view = mock(View.class);
        command = new Help(view);
    }

    @Test
    public void testCreateCanProcess() {
        assertTrue(command.canProcess("help"));
    }

    @Test
    public void testHelp() {
        command.process("help");
        verify(view).write("Existing commands:");

        verify(view).write("\tconnect|databaseName|username|password");
        verify(view).write("\t\tto connect to database");

        verify(view).write("\tcreate|tableName|column1|column2|...|columnN");
        verify(view).write("\t\tto create table with columns");

        verify(view).write("\tclear|tableName");
        verify(view).write("\t\tto clear of table's content");

        verify(view).write("\tdrop|tableName");
        verify(view).write("\t\tto delete table");

        verify(view).write("\tdelete|tableName|column|value");
        verify(view).write("\t\tcommand deletes records for which the condition is satisfied column = value");

        verify(view).write("\tinsert|tableName|column1|value1|column2|value2|columnN|valueN");
        verify(view).write("\t\tto insert row into the table");

        verify(view).write("\tupdate|tableName|column1|value1|column2|value2|columnN|valueN");
        verify(view).write("\t\tcommand updates the record by setting the column value2 = the value2 for which the condition is satisfied column1 = value1");

        verify(view).write("\tlist");
        verify(view).write("\t\tto get list of tables");

        verify(view).write("\tfind|tableName");
        verify(view).write("\t\tto get content of table 'tableName'");

        verify(view).write("\thelp");
        verify(view).write("\t\tto display list of command");

        verify(view).write("\texit");
        verify(view).write("\t\tto exit the program");
    }
}


