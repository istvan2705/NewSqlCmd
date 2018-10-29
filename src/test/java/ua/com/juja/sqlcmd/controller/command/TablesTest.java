package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;


public class TablesTest {

    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        if (manager.isConnected()) {
            command = new Tables(manager);
        }
    }

    @Test
    public void testTables() throws SQLException {
        when(manager.getTableNames()).thenReturn(new LinkedHashSet<>(Arrays.asList("teachers", "tvboxes", "tv", "tvset")));
        assertEquals(new LinkedHashSet<>(Arrays.asList("teachers", "tvboxes", "tv", "tvset")), manager.getTableNames());
    }
}