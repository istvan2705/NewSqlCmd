package ua.com.juja.sqlcmd.controller.command;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.controller.JDBCDatabaseManager;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JDBCDatabaseManagerTest {

    private DatabaseManager manager;

    @Before
    public void setup() throws SQLException {
        manager = new JDBCDatabaseManager();
        manager.connect("Academy", "postgres", "1401198n");
    }

    @Test
    public void testGetAllTableNames() throws SQLException {
        Set<String> tableNames = manager.getTableNames();
        assertEquals("[students, teachers, players]", tableNames.toString());
    }

    @Test
    public void testClearTableIfExists() throws SQLException {
        String tableName = "teachers";
        manager.insert(tableName, Arrays.asList("id", "surname"), Arrays.asList("1", "Kish"));
        assertTrue(manager.clear(tableName));
    }

    @Test
    public void testGetTableColumns() throws SQLException {
        String tableName = "teachers";
        Set<String> columns = manager.getColumnsNames(tableName);
        assertEquals("[id, surname]", columns.toString());
    }

    @Test
    public void testGetTableRows() throws SQLException{
        String tableName = "teachers";
        List<String> rows = manager.getTableRows(tableName);
        assertEquals("[2, Petrov]", rows.toString().trim());
    }
}


