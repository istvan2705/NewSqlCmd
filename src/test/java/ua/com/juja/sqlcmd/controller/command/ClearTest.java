package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;


public class ClearTest {
    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
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
        command.process("clear|" + tableName);
        verify(manager).clear(tableName);
        verify(view).write("You are trying to clear the contents of an empty table");
    }

    @Test
    public void testNotClearTableIfContentNotExists() {
        String tableName = "teachers";
        try {
            command.process("clear|" + tableName);
            verify(manager).clear(tableName);
        } catch (SQLException e) {
            view.write(String.format("Can not execute command  due to: %s", e.getMessage()));
        }
    }
}


