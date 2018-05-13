package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class isConnectedTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new isConnected(manager, view);
    }

    @Test
    public void testIsConnectCanProcess() {
        assertTrue(command.canProcess("connect|Academy|postgres|1401198n"));
    }

    @Test
    public void isConnectTest() {
        when(!manager.isConnected()).thenReturn(false);
        view.write(String.format("You can not use command '%s' until " + " you will not connect with command " +
                "connect|databaseName|userName|password", command));
    }
} 