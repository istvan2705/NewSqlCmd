package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;


public class UpdateTest {


    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        if (manager.isConnected()) {
            command = new Update(manager);
        }
    }
      @Test
    public void testUpdateIfRowExist() throws SQLException {
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
        when(manager.update(tableName, columns, rows, keyColumn, keyValue)).thenReturn(true);
        assertTrue(manager.update(tableName, columns, rows, keyColumn, keyValue));
        verify(manager).update(tableName, columns, rows, keyColumn, keyValue);
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
        when(manager.update(tableName, columns, rows, keyColumn, keyValue)).thenReturn(false);
        assertFalse(manager.update(tableName, columns, rows, keyColumn, keyValue));
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