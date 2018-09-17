package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DropTest {
    private DataSet data;
    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() {
        data = mock(DataSet.class);
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Drop(data, manager, view);
    }

    @Test
    public void testDropTableProcess() throws SQLException {
        String tableName = "students";
        String input = "drop|"+ tableName;

        when(data.getParameters(input)).thenReturn(new ArrayList<>(Arrays.asList("drop", "students")));
        when(data.getTableName(input)).thenReturn("students");
        command.process(input);

        verify(data).getParameters(input);
        verify(data).getTableName(input);
        verify(manager).deleteTable(tableName);
        verify(view).write("The table 'students' has been deleted");
    }
}