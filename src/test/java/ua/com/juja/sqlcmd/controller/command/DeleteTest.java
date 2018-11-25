package ua.com.juja.sqlcmd.controller.command;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class DeleteTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Delete(manager, view);
    }

    @Test
    public void testDeleteIfRowExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "subject";
        String rowName = "History";
        when(manager.deleteRows(tableName, columnName, rowName)).thenReturn(true);
        command.execute("delete|" + tableName + "|" + columnName + "|" + rowName);
        verify(manager).deleteRows(tableName, columnName, rowName);
        verify(view).write("The row has been deleted");
    }

    @Test
    public void testDeleteIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "city";
        String rowName = "Lviv";
        when(manager.deleteRows(tableName, columnName, rowName)).thenReturn(false);
        command.execute("delete|" + tableName + "|" + columnName + "|" + rowName);
        verify(manager).deleteRows(tableName, columnName, rowName);
        verify(view).write(String.format("Error entering command. The row value '%s' does not exist", rowName));
    }


    @Test(expected = SQLException.class)
    public void testIfTableNotExist() throws SQLException {
        String tableName = "teachers";
        String columnName = "city";
        String rowName = "Lviv";
        doThrow(new SQLException()).when(manager).deleteRows(tableName, columnName, rowName);
        manager.deleteRows(tableName, columnName, rowName);
        verify(manager).deleteRows(tableName, columnName, rowName);

    }
}


