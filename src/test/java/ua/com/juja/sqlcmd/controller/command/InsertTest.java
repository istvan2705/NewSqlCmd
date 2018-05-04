package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;


    public class InsertTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

     @Before
    public void init(){
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
    public void testInsert() throws SQLException {
     String tableName = "teachers";
     String key = "id";
     DataSet set = new DataSet();
     set.put("id", "3");
     set.put("surname", "Ivanov");
     set.put("subject", "History");
     set.put("city", "Lviv");

     command.process("insert|teachers|id|3|surname|Ivanov|subject|History|city|Lviv");
        verify(manager).insert(eq(tableName),any(DataSet.class), eq(key));
     verify(view).write(String.format("Statement are added into the table '%s'", tableName));


    }



}