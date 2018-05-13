package ua.com.juja.sqlcmd.controller.command;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Delete(manager, view);
    }

    @Test
    public void testDeleteCanProcess() {
        assertTrue(command.canProcess("delete|teachers|subject|Math"));
    }

    @Test
    public void testDeleteCanProcessError() {
        assertFalse(command.canProcess("delete"));
    }

    @Test
    public void testDeleteIfRowExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "subject";
        String rowName = "Math";
        when(manager.deleteRows(tableName, columnName, rowName)).thenReturn(true);
        command.process("delete|teachers|subject|Math");
        verify(manager).deleteRows(tableName, columnName, rowName);
        verify(view).write("The row has been deleted");
    }

    @Test
    public void testDeleteIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "subject";
        String rowName = "Math";
        when(!manager.deleteRows(tableName, columnName, rowName)).thenReturn(false);
        command.process("delete|teachers|subject|Math");
        verify(manager).deleteRows(tableName, columnName, rowName);
        verify(view).write(String.format("Error entering command. The row with rowName  '%s' does not exist", rowName));
    }
}