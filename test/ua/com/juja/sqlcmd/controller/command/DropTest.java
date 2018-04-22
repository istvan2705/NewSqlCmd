package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DropTest {
    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Drop(manager, view);
    }

    @Test
    public void testDropCanProcess() {
        assertTrue(command.canProcess("drop|students"));
    }

    @Test
    public void testDropCanProcessError() {
        assertFalse(command.canProcess("drop"));
    }

    @Test
    public void testDropTableProcess() throws SQLException {
        String tableName = "students";
        command.process("drop|" + tableName);
        verify(manager).deleteTable("drop|" + tableName);
        verify(view).write("The table has been deleted");
    }

    @Test
    public void testDropWrongParameters() {
        try {
            command.process("drop");
        } catch (ArrayIndexOutOfBoundsException e) {
            //do nothing
        }
        verify(view).write("Error entering command, should be like drop|tableName");
    }



} 