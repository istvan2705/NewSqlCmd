package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

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

      @Test(expected = SQLException.class)
    public void testUpdateIfRowNotExist() throws SQLException {
        String tableName = "teachers";
        String keyColumn = "id";
        String keyValue = "2";
        String column = "surname";
        String value = "Petrov";
        doThrow(new SQLException()).when(manager).update(tableName, keyColumn, keyValue, column, value);
        manager.update(tableName, keyColumn, keyValue, column, value);
        verify(manager).update(tableName, keyColumn, keyValue, column, value);
        verify(view).write("The row has been not updated, because it does not exist");
    }
}

