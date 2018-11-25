package ua.com.juja.sqlcmd.controller.command;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.*;

public class InsertTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Insert(manager, view);
    }


    @Test
    public void testInsertIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        List<String> columns = new ArrayList<>();
        String column1 = "id";
        String column2 = "surname";
        columns.add(column1);
        columns.add(column2);
        List<Object> rows = new ArrayList<>();
        String row1 = "3";
        String row2 = "Ivanov";
        rows.add(row1);
        rows.add(row2);
        command.execute("insert|" + tableName + "|" + column1 + "|" + row1 + "|" + column2 + "|" + row2);
        verify(manager).insert(tableName, columns, rows);
    }


    @Test(expected = SQLException.class)
    public void testIfRowExists() throws SQLException {
        String tableName = "teachers";
        List<String> columns = new ArrayList<>();
        String column1 = "id";
        String column2 = "surname";
        columns.add(column1);
        columns.add(column2);
        List<Object> rows = new ArrayList<>();
        String row1 = "3";
        String row2 = "Ivanov";
        rows.add(row1);
        rows.add(row2);
        doThrow(new SQLException()).when(manager).insert(tableName, columns, rows);
        manager.insert(tableName, columns, rows);
        verify(manager).insert(tableName, columns, rows);
    }
}