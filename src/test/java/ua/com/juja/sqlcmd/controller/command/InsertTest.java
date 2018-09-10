package ua.com.juja.sqlcmd.controller.command;


import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static ua.com.juja.sqlcmd.controller.command.Command.SQL_EXCEPTION_MESSAGE;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.awt.List;
import java.sql.Array;
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
        assertTrue(command.canProcess("insert|teachers|id|3|surname|Ivanov"));
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

        ArrayList<String> list = new ArrayList<>();
        list.add(column1);
        list.add(value1);
        list.add(column2);
        list.add(value2);

        Map<String, String> map = new HashMap<>();
        map.put(column1, value1);
        map.put(column2, value2);

        when(data.getParameters(input)).thenReturn(new ArrayList(Arrays.asList("insert", "teachers", "id", "3", "surname", "Ivanov")));
        when(data.getTableName(input)).thenReturn("teachers");
        when(data.getTableData(input)).thenReturn(new ArrayList(Arrays.asList("id", "3", "surname", "Ivanov")));
        when(data.setValuesToColumns(list)).thenReturn(map);

        command.process(input);

        verify(data).getParameters(input);
        verify(data).getTableName(input);
        verify(data).getTableData(input);

        verify(data).setValuesToColumns(list);
        verify(manager).insert(tableName, map);
        verify(view).write(String.format("Statement are added into the table '%s'", tableName));
    }

    @Test(expected = SQLException.class)
    public void testInsertIfRowExists() throws SQLException {
        String tableName = "teachers";
        String column1 = "id";
        String value1 = "3";
        String column2 = "surname";
        String value2 = "Ivanov";
        String input = "insert|" + tableName + "|" + column1 + "|" + value1 + "|" + column2 + "|" + value2;

        Map<String, String> map = new HashMap<>();
        map.put(column1, value1);
        map.put(column2, value2);

        command.process(input);

        doThrow(new SQLException()).when(manager).insert(tableName, map);
        manager.insert(tableName, map);
    }
}