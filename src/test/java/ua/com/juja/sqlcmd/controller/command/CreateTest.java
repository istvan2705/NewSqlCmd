package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateTest {
    private View view;
    private DatabaseManager manager;
    private Command command;

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
    public void testNotCreateIfExists() throws SQLException{
    String tableName = "students";
    when(!manager.tableExist(tableName)).thenReturn(true);
    command.process("create|students|id|surname");
    verify(view).write(String.format("The table '%s' already exist", tableName));
    }


    @Test
    public void testCreateIfNotExists() throws SQLException {

            String tableName = "workers";

            DataSet columns = new DataSet();
            columns.put("id",2);
            columns.put("surname", 3);
           when(manager.tableExist(tableName)).thenReturn(false);
            command.process("create|workers|id|surname");
            verify(manager).create(eq(tableName), any(DataSet.class));
            verify(view).write(String.format("The table '%s' has been created", tableName));
        }


    }

