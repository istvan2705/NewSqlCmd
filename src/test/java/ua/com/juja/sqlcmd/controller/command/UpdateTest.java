package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static ua.com.juja.sqlcmd.controller.command.Command.SQL_EXCEPTION_MESSAGE;


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

    @Test
    public void testUpdateCanProcess() {
        assertTrue(command.canProcess("update|teachers|id|3|surname|Bogdanov"));
    }

    @Test
    public void testUpdateCanProcessError() {
        assertFalse(command.canProcess("update"));
    }

    @Test
    public void testUpdateIfRowExist() {
        String tableName = "teachers";
        String updatedColumn = "surname";
        String updatedValue = "Pavlov";
        Map<String, String> set = new LinkedHashMap<>();
        set.put(updatedColumn, updatedValue);
        set.put("subject", "Geometry");
        command.process("update|teachers|surname|Pavlov|subject|Geometry");
        try {
            boolean isUpdate = manager.update(tableName, updatedColumn, updatedValue, set);
            if (isUpdate) {
                verify(view).write("The row has been updated");
            }
        } catch (SQLException e) {
            verify(view).write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }

    }

    @Test
    public void testUpdateIfRowNotExist() {
        String tableName = "teachers";
        String updatedColumn = "id";
        String updatedValue = "8";
        Map<String, String> set = new LinkedHashMap<>();
        set.put(updatedColumn, updatedValue);
        set.put("subject", "Geometry");

        command.process("update|teachers|id|8|subject|Geometry");
        try {
            boolean isUpdate = manager.update(tableName, updatedColumn, updatedValue, set);
            if (isUpdate) {
                verify(view).write("The row has been not updated due to incorrect parameter");
            }
        } catch (SQLException e) {
            verify(view).write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }

    }

}