package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;

public class ExitTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Exit(view);
    }

    @Test
    public void testProcessExit() throws SQLException {
        command.execute("exit|");
        Mockito.verify(view).write("See you soon!");
    }
}

