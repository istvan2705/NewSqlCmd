package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTest  {
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
       List<String> list = new ArrayList<>();
       list.add("id");
       list.add("surname");
       list.add("name");

        command.process("create|students|id|surname|name");
        verify(manager).create(tableName,list);
        view.write(String.format("The table '%s' has been created", tableName));
    }

    @Test
    public void testNotCreateIfExists() throws SQLException {
        String tableName = "students";
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("surname");
        list.add("name");
        command.process("create|students|id|surname|name");
        verify(manager).create(tableName, list);
        view.write(String.format("The table '%s' already exist", tableName));
    }
}

