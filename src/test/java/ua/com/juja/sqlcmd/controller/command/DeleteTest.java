package ua.com.juja.sqlcmd.controller.command;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
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
        String tableName = "players";
        String columnName = "surname";
        String rowName = "Kish";
       command.execute("delete|" + tableName + "|" + columnName + "|" + rowName);
        verify(manager).deleteRows(tableName, columnName, rowName);
        verify(view).write("Entered row does not exist for column: surname");
    }

    @Test
    public void testDeleteIfRowNotExists() throws SQLException {
        String tableName = "players";
        String columnName = "id";
        String rowName = "12";
        command.execute("delete|" + tableName + "|" + columnName + "|" + rowName);
        verify(manager).deleteRows(tableName, columnName, rowName);
       verify(view).write(String.format("Entered row does not exist for column: %s", columnName));
    }

    @Test(expected = SQLException.class)
    public void testIfTableNotExist() throws SQLException {
        String tableName = "teacher";
        String columnName = "city";
        String rowName = "Lviv";
        doThrow(new SQLException()).when(manager).deleteRows(tableName, columnName, rowName);
        manager.deleteRows(tableName, columnName, rowName);
        verify(manager).deleteRows(tableName, columnName, rowName);

    }
}


