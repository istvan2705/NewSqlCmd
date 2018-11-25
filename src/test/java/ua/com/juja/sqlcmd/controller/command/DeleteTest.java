package ua.com.juja.sqlcmd.controller.command;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
        command = new Delete(manager, view);
    }

   @Test
    public void testDeleteIfRowExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "subject";
        String rowName = "History";
        when(manager.deleteRows(tableName, columnName, rowName)).thenReturn(true);
        command.execute();
        verify(manager).deleteRows(tableName, columnName, rowName);
        verify(view).write("The row has been deleted");
    }

    @Test
    public void testDeleteIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "city";
        String rowName = "Lviv";
        when(!manager.deleteRows(tableName, columnName, rowName)).thenReturn(false);
        try {
            command.execute();
            verify(manager).deleteRows(tableName, columnName, rowName);
            verify(view).write(String.format("Error entering command. The row with rowName  '%s' does not exist", rowName));
        } catch (SQLException e) {
            verify(view).write(String.format("Can not execute command  due to: %s", e.getMessage()));
        }
    }
}