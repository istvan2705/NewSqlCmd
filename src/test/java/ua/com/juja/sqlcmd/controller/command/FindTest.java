package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;

public class FindTest {
    public DataSet data;
    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        data = mock(DataSet.class);
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(data, manager, view);
    }

    @Test
    public void testFindCanProcess() {
        assertTrue(command.canProcess("find|students"));
    }

    @Test
    public void testFindCanProcessError() {
        assertFalse(command.canProcess("find"));
    }

    @Test
    public void testFind() throws SQLException {
        String tableName = "students";
        String input = "find|" + tableName;

        when(data.getParameters(input)).thenReturn(new ArrayList(Arrays.asList("find", "students")));
        when(data.getTableName(input)).thenReturn("students");
        when(manager.getColumnsNames("students")).thenReturn(new LinkedHashSet<>(Arrays.asList("id", "surname", "subject", "city")));
        DataSetImpl set = new DataSetImpl();
        set.put("id", "1");
        set.put("surname", "Petrov");
        set.put("subject", "History");
        set.put("city", "Lviv");
        when(manager.getTableRows("students")).thenReturn(Arrays.asList(set));

        command.process(input);
        verify(data).getParameters(input);
        verify(data).getTableName(input);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(
                "[--------------------------, " +
                        "|id||surname||subject||city|, " +
                        "--------------------------, " +
                        "|1||Petrov||History||Lviv|, " +
                        "--------------------------]", captor.getAllValues().toString());
    }
}