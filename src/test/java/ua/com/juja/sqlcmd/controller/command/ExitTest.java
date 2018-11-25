package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.mock;

public class ExitTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException{
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
        command = new Exit(view);
    }



//    @Test
//    public void testProcessExit() {
//        command.process("exit");
//        Mockito.verify(view).write("See you soon!");
//        System.exit(0);
//    }
}
