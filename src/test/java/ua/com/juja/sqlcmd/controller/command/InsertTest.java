package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InsertTest {


    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        if (manager.isConnected()) {
            command = new Insert(manager);
        }
    }

    @Test
    public void testInsertIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        String column1 = "id";
        String column2 = "surname";
        String row1 = "4";
        String row2 = "Petrov";
        List<String> columns = new ArrayList<>();
        columns.add(column1);
        columns.add(column2);
        List<Object> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        manager.insert(tableName, columns, rows);
        verify(manager).insert(tableName, columns, rows);
    }

    @Test(expected = SQLException.class)
    public void testInsertIfRowExists() throws SQLException {
        String tableName = "teachers";
        String column1 = "id";
        String column2 = "surname";
        String row1 = "4";
        String row2 = "Petrov";
        List<String> columns = new ArrayList<>();
        columns.add(column1);
        columns.add(column2);
        List<Object> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        doThrow(new SQLException()).when(manager).insert(tableName, columns, rows);
        manager.insert(tableName, columns, rows);
    }
}