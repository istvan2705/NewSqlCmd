package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class TablesTest {

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
    public void testTables() throws SQLException {
        when(manager.getTableNames()).thenReturn(new LinkedHashSet<>(Arrays.asList("teachers", "students")));
        command.execute("tables");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[[teachers, students]]", captor.getAllValues().toString());
    }

    @Test(expected = SQLException.class)
    public void testTableIfNotExists() throws SQLException {
        doThrow(new SQLException()).when(manager).getTableNames();
        manager.getTableNames();
        verify(manager).getTableNames();
    }
}