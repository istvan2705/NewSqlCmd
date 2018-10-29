package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DropTest {

    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        if (manager.isConnected()) {
            command = new Drop(manager);
        }
    }

    @Test
    public void testDropTableIfExists() throws SQLException {
        String tableName = "students";
        manager.deleteTable(tableName);
        verify(manager).deleteTable(tableName);
    }

    @Test(expected = SQLException.class)
    public void testDropTableIfNotExists() throws SQLException {
        String tableName = "players";
        doThrow(new SQLException()).when(manager).deleteTable(tableName);
        manager.deleteTable(tableName);
    }
}