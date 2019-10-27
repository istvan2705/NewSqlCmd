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


public class /**/CreateTest {
    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Create(manager, view);
    }

    @Test
    public void testCreateIfNotExists() throws SQLException {
        String tableName = "teachers";
        String column1 = "id";
        String column2 = "surname";
        String column3 = "name";
        List<String> list = new ArrayList<>();
        list.add(column1);
        list.add(column2);
        list.add(column3);
        command.execute("create|" + tableName + "|" + column1 + "|" + column2 + "|" + column3);

        verify(manager).create(tableName, list);
        verify(view).write(String.format("The table '%s' has been created", tableName));
    }

    @Test(expected = SQLException.class)
    public void testNotCreateIfExists() throws SQLException {
        String tableName = "teachers";
        String column1 = "id";
        String column2 = "surname";
        String column3 = "name";
        List<String> list = new ArrayList<>();
        list.add(column1);
        list.add(column2);
        list.add(column3);
        doThrow(new SQLException()).when(manager).create(tableName, list);
        manager.create(tableName, list);
        verify(manager).create(tableName, list);
    }
}


