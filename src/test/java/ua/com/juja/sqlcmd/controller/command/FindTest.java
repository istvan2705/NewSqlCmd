package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;


import static org.junit.Assert.assertEquals;

public class FindTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(manager, view);
    }

    @Test
    public void testFind() throws SQLException {
        String tableName = "teachers";
        when(manager.getColumnsNames("teachers")).thenReturn(new LinkedHashSet<>(Arrays.asList("id", "surname", "name", "subject")));
        when(manager.getTableRows(tableName)).thenReturn(new LinkedList<>(Arrays.asList("1", "Pavlov", "Ivan", "history")));
        command.execute("find|" + tableName);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(
                "[--------------------------\n" +
                        "|id||surname||name||subject|\n" +
                        "--------------------------\n" +
                        "|1||Pavlov||Ivan||history|]", captor.getAllValues().toString());
    }
}