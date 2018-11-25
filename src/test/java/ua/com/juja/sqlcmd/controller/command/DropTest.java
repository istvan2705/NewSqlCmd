package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DropTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Drop(manager, view);
    }

    @Test
    public void testDropTableProcess() throws SQLException {
        String tableName = "students";
        command.execute("drop|" + tableName);
        verify(manager).deleteTable(tableName);
        verify(view).write("The table 'students' has been deleted");
    }

    @Test(expected = SQLException.class)
    public void testDropTableIfNotExists() throws SQLException {
        String tableName = "students";
        doThrow(new SQLException()).when(manager).deleteTable(tableName);
        manager.deleteTable(tableName);
    }
}