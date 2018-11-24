package ua.com.juja.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.util.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class DatabaseManagerMockitoTest {

    private DatabaseManager manager;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        }


    @Test
    public void testClearTableIfContentExists() throws SQLException {
        String tableName = "teachers";
        when(manager.clear(tableName)).thenReturn(true);

        assertTrue(manager.clear(tableName));

        verify(manager).clear(tableName);

    }

    @Test
    public void testClearTableIfContentNotExists() throws SQLException {
        String tableName = "tv";
        when(manager.clear(tableName)).thenReturn(false);

        assertFalse(manager.clear(tableName));

        verify(manager).clear(tableName);
    }

    @Test
    public void testClearTableIfTableNotExists() throws SQLException {
        String tableName = "players";
        doThrow(new SQLException()).when(manager).clear(tableName);
    }
    @Test
    public void testConnectSuccessful() throws SQLException {
        String databaseName = "Academy";
        String userName = "postgres";
        String password = "1401198n";
        manager.connect(databaseName, userName, password);
        verify(manager).connect(databaseName, userName, password);
    }

    @Test
    public void testConnectFailed() throws SQLException {
        String databaseName = "Users";
        String userName = "postgres";
        String password = "14011981";
        manager.connect(databaseName, userName, password);
        verify(manager).connect(databaseName, userName, password);
    }
    @Test
    public void testCreateIfNotExists() throws SQLException {
        String tableName = "students";
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("surname");
        list.add("name");
        manager.create(tableName, list);
        verify(manager).create(tableName, list);
    }

    @Test(expected = SQLException.class)
    public void testNotCreateIfExists() throws SQLException {
        String tableName = "students";
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("surname");
        list.add("name");
        doThrow(new SQLException()).when(manager).create(tableName, list);
        manager.create(tableName, list);
    }

    @Test
    public void testDeleteIfRowExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "subject";
        String rowName = "History";
        when(manager.deleteRows(tableName, columnName, rowName)).thenReturn(true);
        assertTrue(manager.deleteRows(tableName, columnName, rowName));
        verify(manager).deleteRows(tableName, columnName, rowName);
    }

    @Test
    public void testDeleteIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        String columnName = "city";
        String rowName = "Lviv";
        when(!manager.deleteRows(tableName, columnName, rowName)).thenReturn(false);
        assertFalse(manager.deleteRows(tableName, columnName, rowName));
        verify(manager).deleteRows(tableName, columnName, rowName);
    }

    @Test(expected = SQLException.class)
    public void testDeleteIfTableNotExists() throws SQLException {
        String tableName = "players";
        String columnName = "city";
        String rowName = "Lviv";
        doThrow(new SQLException()).when(manager).deleteRows(tableName, columnName, rowName);
        manager.deleteRows(tableName, columnName, rowName);
    }

    @Test
    public void testDropTableIfExists() throws SQLException {
        String tableName = "students";
        manager.deleteTable(tableName);
        verify(manager).deleteTable(tableName);
    }

    @Test(expected = SQLException.class)
    public void testDropTableIfNotExists() throws SQLException {
        String tableName = "players";
        doThrow(new SQLException()).when(manager).deleteTable(tableName);
        manager.deleteTable(tableName);
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
    @Test
    public void testTables() throws SQLException {
        when(manager.getTableNames()).thenReturn(new LinkedHashSet<>(Arrays.asList("teachers", "tvboxes", "tv", "tvset")));
        assertEquals(new LinkedHashSet<>(Arrays.asList("teachers", "tvboxes", "tv", "tvset")), manager.getTableNames());
    }
    @Test
    public void testUpdateIfRowNotExist() throws SQLException {
        String tableName = "teachers";
        String keyColumn = "id";
        String keyValue = "2";
        String column1 = "surname";
        String column2 = "name";
        String row1 = "Pavlov";
        String row2 = "Ivan";
        List<String> columns = new ArrayList<>();
        columns.add(column1);
        columns.add(column2);
        List<Object> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        manager.update(tableName, columns, rows, keyColumn, keyValue);
        verify(manager).update(tableName, columns, rows, keyColumn, keyValue);
    }


    @Test(expected = SQLException.class)
    public void testUpdateIfTableNotExist() throws SQLException {
        String tableName = "teachers";
        String keyColumn = "id";
        String keyValue = "2";
        String column1 = "surname";
        String column2 = "name";
        String row1 = "Pavlov";
        String row2 = "Ivan";
        List<String> columns = new ArrayList<>();
        columns.add(column1);
        columns.add(column2);
        List<Object> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        doThrow(new SQLException()).when(manager).update(tableName, columns, rows, keyColumn, keyValue);
        manager.update(tableName, columns, rows, keyColumn, keyValue);
    }
    
}






