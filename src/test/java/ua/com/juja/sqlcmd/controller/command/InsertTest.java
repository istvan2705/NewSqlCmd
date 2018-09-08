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
import java.util.*;

public class InsertTest {
    public DataSet data;
    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        data = mock(DataSet.class);
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Insert(data, manager, view);
    }

    @Test
    public void testInsertCanProcess() {
        assertTrue(command.canProcess("insert|teachers|id|3|surname|Bogdanov"));
    }

    @Test
    public void testInsertCanProcessError() {
        assertFalse(command.canProcess("insert"));
    }

    @Test
    public void testInsertIfRowNotExists() throws SQLException {
        String tableName = "teachers";
        String column1 = "id";
        String value1 = "3";
        String column2 = "surname";
        String value2 = "Ivanov";

        String input = "insert|" + tableName + "|" + column1 + "|" + value1 + "|" + column2 + "|" + value2;

        Map<String, String> map = new LinkedHashMap<>();
        map.put(column1, value1);
        map.put(column2, value2);
        when(data.getParameters(input)).thenReturn(new ArrayList(Arrays.asList("insert", "teachers", "id", "3",
                "surname", "Ivanov")));
        when(data.getTableName(input)).thenReturn("teachers");
        when(data.getTableData(input)).thenReturn(new ArrayList(Arrays.asList(map)));

        command.process(input);

        verify(data).getParameters(input);
        verify(data).getTableName(input);
        verify(data).getTableData(input);
        verify(manager).insert(tableName, map);
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