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
import static org.mockito.Mockito.*;

public class ConnectTest {
    private DataSet data;
    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void init() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        data = mock(DataSet.class);
        command = new Connect(data, manager, view);
    }

    @Test
    public void testConnectCanProcess() {
        assertTrue(command.canProcess("connect|Academy|postgres|1401198n"));
    }

    @Test
    public void testConnectCanProcessError() {
        assertFalse(command.canProcess("connect"));
    }

    @Test

    public void testConnectSuccessful() throws SQLException {
        String tableName = "Academy";
        String column1 = "postgres";
        String column2 = "1401198n";
        String input = "connect|" + tableName + "|" + column1 + "|" + column2;

        when(data.getParameters(input)).thenReturn(new ArrayList(Arrays.asList("connect", "Academy", "postgres", "1401198n")));
        when(data.getTableName(input)).thenReturn("Academy");
        when(data.getTableData(input)).thenReturn(new ArrayList(Arrays.asList("postgres", "1401198n")));
        command.process(input);
        verify(data).getParameters(input);
        verify(data).getTableName(input);
        verify(data).getTableData(input);
        verify(manager).connect(tableName, column1, column2);
        view.write(String.format("You have login to database '%s' successfully!", tableName));
    }

    @Test(expected = SQLException.class)
    public void testConnectFailed() throws SQLException {
        String tableName = "Academy";
        String column1 = "postgres";
        String column2 = "1401198n";
        String input = "connect|" + tableName + "|" + column1 + "|" + column2;

        command.process(input);

        doThrow(new SQLException()).when(manager).connect(tableName, column1, column2);
        manager.connect(tableName, column1, column2);
    }
}

