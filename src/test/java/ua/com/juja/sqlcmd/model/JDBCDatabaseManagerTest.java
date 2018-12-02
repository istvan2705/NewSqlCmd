package ua.com.juja.sqlcmd.model;


import org.junit.Before;
import org.junit.Test;
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
        assertEquals("[tvsets, teachers]", tableNames.toString());
    }

    @Test
    public void testClearTableIfExists() throws SQLException {
        String tableName = "teachers";
        manager.insert(tableName, Arrays.asList("id", "surname", "name"), Arrays.<Object>asList("1", "Kish", "Stepan"));
        assertTrue(manager.clear(tableName));
    }

    @Test
    public void testDeleteTableRow() throws SQLException {
        String tableName = "teachers";
        String column = "id";
        String row = "1";
        manager.insert(tableName, Arrays.asList("id", "surname", "name"), Arrays.<Object>asList("1", "Kish", "Stepan"));

        assertTrue(manager.deleteRows(tableName, column, row));
    }

    @Test
    public void testGetTableColumns() throws SQLException {
        String tableName = "tvsets";
        Set<String> columns = manager.getColumnsNames(tableName);
        assertEquals("[id, model, date, price]", columns.toString());
    }

    @Test
    public void testGetTableRows() throws SQLException{
        String tableName = "tvsets";
        List<String> columns = manager.getTableRows(tableName);
        assertEquals("[1, 12, oct12, 45, \n" +
                ", 2, x96, oct15, 50, \n" +
                "]", columns.toString());

    }
}


