package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class UpdateTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Update(manager, view);
    }

    @Test
    public void testUpdateIfRowExist() throws SQLException {
        String tableName = "teachers";
        String keyColumn = "id";
        String keyValue = "3";
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
        command.execute("update|" + tableName + "|" + column1 + "|" + row1 + "|" + column2 + "|" + row2);
        verify(manager).update(tableName, columns, rows, keyColumn, keyValue);
        verify(view).write("The row has been updated");
    }

    @Test(expected = SQLException.class)
    public void testUpdateIfRowNotExist() throws SQLException {
        String tableName = "teachers";
        String keyColumn = "surname";
        String keyValue = "Ivanov";
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
        doThrow(new SQLException()).when(manager).update(tableName, columns, rows, keyColumn, keyValue);
        manager.update(tableName, columns, rows, keyColumn, keyValue);
        verify(manager).update(tableName, columns, rows, keyColumn, keyValue);
    }
}

