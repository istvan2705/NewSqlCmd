package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class CreateTest {
    public DataSet data;
    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        data = mock(DataSet.class);
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Create(data, manager, view);
    }

    @Test
    public void testCreateCanProcess() {
        assertTrue(command.canProcess("create|workers|id|surname"));
    }

    @Test
    public void testCreateCanProcessError() {
        assertFalse(command.canProcess("create"));
    }


    @Test
    public void testCreateIfNotExists() throws SQLException {
        String tableName = "students";
        String column1 = "id";
        String column2 = "surname";
        String column3 = "name";
        String input = "create|" + tableName + "|" + column1 + "|" + column2 + "|" + column3;
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("surname");
        list.add("name");

        when(data.getParameters(input)).thenReturn(new ArrayList(Arrays.asList("create", "students", "id", "surname", "name")));
        when(data.getTableName(input)).thenReturn("students");
        when(data.getTableData(input)).thenReturn(new ArrayList(Arrays.asList("id", "surname", "name")));

        command.process("create|students|id|surname|name");

        verify(data).getParameters(input);
        verify(data).getTableName(input);
        verify(data).getTableData(input);
        verify(manager).create(tableName, list);
        verify(view).write(String.format("The table '%s' has been created", tableName));
    }

    @Test(expected = SQLException.class)
    public void testNotCreateIfExists() throws SQLException {

        String tableName = "students";
        String column1 = "id";
        String column2 = "surname";
        String column3 = "name";
        String input = "create|" + tableName + "|" + column1 + "|" + column2 + "|" + column3;
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("surname");
        list.add("name");

        command.process(input);

        doThrow(new SQLException()).when(manager).create(tableName, list);
        manager.create(tableName, list);
    }
}

