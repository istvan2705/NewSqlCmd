package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.*;
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
    public void testCanProcessExit() {
        //when
        boolean canProcess = command.canProcess("exit");
        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessExit() {
        boolean canProcess = command.canProcess("tree");
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExit_throwsExitException() {
        try {
            command.process("exit");
            fail("Excepted ExitException");
        } catch (ExitException e) {
            //do nothing
        }
        Mockito.verify(view).write("See you soon!");
    }
}

