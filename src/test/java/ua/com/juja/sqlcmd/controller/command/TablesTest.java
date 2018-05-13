package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;


public class TablesTest  {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Tables(manager, view);
    }

    @Test
    public void testTablesCanProcess() {
        assertTrue(command.canProcess("tables"));
    }

    @Test
    public void testTablesCanProcessError() {
        assertFalse(command.canProcess("table"));
    }

    @Test
    public void testTables() throws SQLException {
        when(manager.getTableNames()).thenReturn(new LinkedHashSet<>(Arrays.asList("teachers", "students")));
        command.process("tables");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[[teachers, students]]", captor.getAllValues().toString());
    }
}