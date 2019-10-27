package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static org.mockito.Mockito.*;


public class ClearTest {
    private View view;
    private DatabaseManager manager;
    private Command command;


    @Before
    public void init()  {

        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }

    @Test
    public void testClearTableIfContentExists() throws SQLException {
        String tableName = "teachers";
        when(manager.clear(tableName)).thenReturn(true);
        command.execute("clear|" + tableName);
        verify(manager).clear(tableName);
        verify(view).write(String.format("The content of table '%s' has been deleted", tableName));
    }

    @Test
    public void testClearTableIfTableNotExists() throws SQLException {
        String tableName = "tvboxes";
        when(manager.clear(tableName)).thenReturn(false);
        command.execute("clear|" + tableName);
        verify(manager).clear(tableName);
        verify(view).write("You are trying to clear the contents of an empty table");
    }

    @Test(expected = SQLException.class)
    public void testNotClearTableIfContentNotExists() throws SQLException {
        String tableName = "tvboxes";
        doThrow(new SQLException()).when(manager).clear(tableName);
        manager.clear(tableName);
        verify(manager).clear(tableName);
    }
}


