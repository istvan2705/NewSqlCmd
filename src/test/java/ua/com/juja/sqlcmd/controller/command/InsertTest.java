package ua.com.juja.sqlcmd.controller.command;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static ua.com.juja.sqlcmd.controller.command.Command.SQL_EXCEPTION_MESSAGE;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
    public void testInsertCanProcess() {
        assertTrue(command.canProcess("insert|teachers|id|3|surname|Bogdanov|subject|Geography|city|Kyiv"));
    }

    @Test
    public void testInsertCanProcessError() {
        assertFalse(command.canProcess("insert"));
    }

    @Test
    public void testInsertIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        Map<String,String> set = new LinkedHashMap<>();
        set.put("id", "3");
        set.put("surname", "Ivanov");
        set.put("subject", "History");
        set.put("city", "Lviv");
        command.process("insert|teachers|id|3|surname|Ivanov|subject|History|city|Lviv");
        verify(manager).insert(tableName, set);
        verify(view).write(String.format("Statement are added into the table '%s'", tableName));
    }

    @Test
    public void testInsertIfRowExists() {
        String tableName = "teachers";
        Map<String, String> set = new HashMap<>();
        set.put("id", "3");
        set.put("surname", "Ivanov");
        set.put("subject", "History");
        set.put("city", "Lviv");
        try {
            command.process("insert|teachers|id|3|surname|Ivanov|subject|History|city|Lviv");
            verify(manager).insert(tableName, set);
        } catch (SQLException e) {
            verify(view).write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}