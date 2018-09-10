package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;


public class ClearTest {
    public DataSet data;
    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        data = mock(DataSet.class);
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(data, manager, view);
    }

    @Test
    public void testClearCanProcess() {
        assertTrue(command.canProcess("clear|teachers"));
    }

    @Test
    public void testClearCanProcessError() {
        assertFalse(command.canProcess("clear"));
    }

    @Test
    public void testClearTableIfContentExists() throws SQLException {
        String tableName = "teachers";
        String input = "clear|" + tableName;

        when(data.getParameters(input)).thenReturn(new ArrayList(Arrays.asList("clear", "teachers")));
        when(data.getTableName(input)).thenReturn("teachers");

        command.process("clear|" + tableName);

        verify(data).getParameters(input);
        verify(data).getTableName(input);
        verify(manager).clear(tableName);
        verify(view).write("You are trying to clear the contents of an empty table");
    }

    @Test(expected = SQLException.class)
    public void testNotClearTableIfContentNotExists() throws SQLException{
        String tableName = "teachers";
        String input = "clear|" + tableName;

        command.process(input);

       doThrow(new SQLException()).when(manager).clear(tableName);
       manager.clear(tableName);
    }
}


