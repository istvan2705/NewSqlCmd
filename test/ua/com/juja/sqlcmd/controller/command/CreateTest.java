package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
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
    public void testNotCreateIfExists(){
    String tableName = "students";
    when(manager.tableExist(tableName)).thenReturn(true);
    command.process("create|"+tableName);
    verify(view).write(String.format("Table '%s' already exists", tableName));
    }


    @Test
    public void testCreateWrongParameters() {
        try {
            command.process("create");
        } catch (ArrayIndexOutOfBoundsException e) {
            //do nothing
        }
        verify(view).write("Error entering command,it should be like create|tableName");
    }

} 