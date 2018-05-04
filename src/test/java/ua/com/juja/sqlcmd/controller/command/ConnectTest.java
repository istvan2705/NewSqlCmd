package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class ConnectTest {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Connect(manager, view);
    }

    @Test
    public void testConnectCanProcess() {
        assertTrue(command.canProcess("connect|Academy|postgres|1401198n"));
    }

    @Test
    public void testConnectCanProcessError() {
        assertFalse(command.canProcess("connect"));
    }

    @Test

    public void testConnect() throws SQLException {

        String databaseName = "Academy";
        String userName = "postgres";
        String password = "1401198n";

        command.process("connect|Academy|postgres|1401198n");
        verify(manager).connect(databaseName, userName, password);


        verify(String.format("You have login to database '%s' successfully!", databaseName));
    }

  }
