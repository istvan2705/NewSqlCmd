package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CreateTest {
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        if (manager.isConnected()) {
            command = new Create(manager);
        }
    }

    @Test
    public void testCreateIfNotExists() throws SQLException {
        String tableName = "students";
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("surname");
        list.add("name");
        manager.create(tableName, list);
        verify(manager).create(tableName, list);

    }

    @Test(expected = SQLException.class)
    public void testNotCreateIfExists() throws SQLException {
        String tableName = "students";
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("surname");
        list.add("name");
        doThrow(new SQLException()).when(manager).create(tableName, list);
        manager.create(tableName, list);
    }
}
