package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class DeleteTest {

    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        if (manager.isConnected()) {
            command = new Delete(manager);
        }
    }

    @Test
    public void testDeleteIfRowExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "subject";
        String rowName = "History";
        when(manager.deleteRows(tableName, columnName, rowName)).thenReturn(true);
        assertTrue(manager.deleteRows(tableName, columnName, rowName));
        verify(manager).deleteRows(tableName, columnName, rowName);
    }

    @Test
    public void testDeleteIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "city";
        String rowName = "Lviv";
        when(!manager.deleteRows(tableName, columnName, rowName)).thenReturn(false);
        assertFalse(manager.deleteRows(tableName, columnName, rowName));
        verify(manager).deleteRows(tableName, columnName, rowName);
    }

    @Test(expected = SQLException.class)
    public void testDeleteIfTableNotExists() throws SQLException {
        String tableName = "players";
        String columnName = "city";
        String rowName = "Lviv";
        doThrow(new SQLException()).when(manager).deleteRows(tableName, columnName, rowName);
        manager.deleteRows(tableName, columnName, rowName);
    }
}
