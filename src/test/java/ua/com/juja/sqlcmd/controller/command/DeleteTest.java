package ua.com.juja.sqlcmd.controller.command;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class DeleteTest {
    public DataSet data;
    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        data = mock(DataSet.class);
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Delete(data, manager, view);
    }

    @Test
    public void testDeleteCanProcess() {
        assertTrue(command.canProcess("delete|teachers|subject|Math"));
    }

    @Test
    public void testDeleteCanProcessError() {
        assertFalse(command.canProcess("delete"));
    }

    @Test
    public void testDeleteIfRowExists() throws SQLException {
        String tableName = "teachers";
        String column1 = "subject";
        String column2 = "Math";
        String input = "delete|" + tableName + "|" + column1 + "|" + column2;
        when(data.getParameters(input)).thenReturn(new ArrayList(Arrays.asList("delete", "teachers", "subject", "Math")));
        when(data.getTableName(input)).thenReturn("teachers");
        when(data.getTableData(input)).thenReturn(new ArrayList(Arrays.asList("subject", "Math")));
        when(manager.deleteRows(tableName, column1, column2)).thenReturn(true);

        command.process(input);

        verify(data).getParameters(input);
        verify(data).getTableName(input);
        verify(data).getTableData(input);
        verify(manager).deleteRows(tableName, column1, column2);
        verify(view).write("The row has been deleted");
    }

    @Test(expected = SQLException.class)
    public void testDeleteIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        String column1 = "subject";
        String column2 = "Math";
        String input = "delete|" + tableName + "|" + column1 + "|" + column2;

        command.process(input);
        doThrow(new SQLException()).when(manager).deleteRows(tableName, column1, column2);
        manager.deleteRows(tableName, column1, column2);
        view.write(String.format("Error entering command. The row with rowName  '%s' does not exist", column2));
    }
}