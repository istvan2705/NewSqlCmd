package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class FindTest {

    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        if (manager.isConnected()) {
            command = new Find(manager);
        }
    }

    @Test
    public void testFind() throws SQLException {
        when(manager.getColumnsNames("teachers")).thenReturn(new LinkedHashSet<>(Arrays.asList("id", "surname", "subject", "city")));
        when(manager.getTableRows("teachers")).thenReturn(new LinkedList<>(Arrays.asList("2", "Petrov", "Math", "Lviv")));
        assertEquals(new LinkedHashSet<>(Arrays.asList("id", "surname", "subject", "city")), manager.getColumnsNames("teachers"));
        assertEquals(new LinkedList<>(Arrays.asList("2", "Petrov", "Math", "Lviv")),manager.getTableRows("teachers"));
        verify(manager).getTableRows("teachers");
        verify(manager).getColumnsNames("teachers");
    }
}
