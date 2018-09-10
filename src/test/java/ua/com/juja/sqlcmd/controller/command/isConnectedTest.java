package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class isConnectedTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new IsConnected(manager, view);
    }

    @Test
    public void testIsConnectCanProcess() {
        assertTrue(command.canProcess("connect|Academy|postgres|1401198n"));
    }

    @Test
    public void isConnectTest() {
        when(manager.isConnected()).thenReturn(true);

        manager.isConnected();

        verify(manager).isConnected();
      }
    @Test
    public void testProcessConnect(){
        String input = "connect|Academy|postgres|1401198n";

       command.process(input);

       verify(view).write(String.format("You can not use command '%s' until you have established connection to the "
               + "database connect|databaseName|userName|password", input));
          }
}