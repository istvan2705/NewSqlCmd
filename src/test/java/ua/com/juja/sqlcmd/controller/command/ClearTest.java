package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class ClearTest {


    private DatabaseManager manager;
    Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        if (manager.isConnected()) {
            command = new Clear(manager);
        }
    }

    @Test
    public void testClearTableIfContentExists() throws SQLException {
        String tableName = "teachers";
        when(manager.clear(tableName)).thenReturn(true);
        assertTrue(manager.clear(tableName));
        verify(manager).clear(tableName);
    }

    @Test
    public void testClearTableIfContentNotExists() throws SQLException {
        String tableName = "tv";
        when(manager.clear(tableName)).thenReturn(false);
        assertFalse(manager.clear(tableName));
        verify(manager).clear(tableName);
    }

    @Test
    public void testClearTableIfTableNotExists() throws SQLException {
        String tableName = "players";
        doThrow(new SQLException()).when(manager).clear(tableName);
    }
}


