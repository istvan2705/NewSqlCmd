package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class ConnectTest {

    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        command = new Connect(manager);
    }

    @Test
    public void testConnectSuccessful() throws SQLException {
        String databaseName = "Academy";
        String userName = "postgres";
        String password = "1401198n";
        manager.connect(databaseName, userName, password);
        verify(manager).connect(databaseName, userName, password);
    }

    @Test
    public void testConnectFailed() throws SQLException {
        String databaseName = "Users";
        String userName = "postgres";
        String password = "14011981";
        manager.connect(databaseName, userName, password);
        verify(manager).connect(databaseName, userName, password);
    }
}