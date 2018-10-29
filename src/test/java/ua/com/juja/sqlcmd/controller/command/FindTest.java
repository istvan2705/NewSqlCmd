package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;

public class FindTest {

    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        command = new Find(manager);
    }

    @Test
    public void testFind() throws SQLException {
        when(manager.getColumnsNames("teachers")).thenReturn(new LinkedHashSet<>(Arrays.asList("id", "surname", "subject", "city")));
        assertEquals(new LinkedHashSet<>(Arrays.asList("id", "surname", "subject", "city")), manager.getColumnsNames("teachers"));

        when(manager.getTableRows("teachers")).thenReturn(Arrays.asList("2", "Petrov", "Math", "Lviv"));
        assertEquals((Arrays.asList("2", "Petrov", "Math", "Lviv")), manager.getColumnsNames("teachers"));
    }
}
