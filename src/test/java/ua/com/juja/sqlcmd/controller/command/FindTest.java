package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FindTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
    manager = mock(DatabaseManager.class);
    view = mock(View.class);
    command = new Find(manager, view);


    }
    @Test
    public void testFindCanProcess() {
        assertTrue(command.canProcess("find|teachers"));
    }

    @Test
    public void testFindCanProcessError() {
        assertFalse(command.canProcess("find"));
    }

    @Test
    public void testFind() throws SQLException  {


   when(manager.getColumnsNames("teachers")).thenReturn(new LinkedHashSet<>(Arrays.asList( "id", "surname", "subject", "city")));

   DataSet set = new DataSet();
   set.put("id", 1);
   set.put("surname", "Petrov");
   set.put("subject", "History");
   set.put("city", "Lviv");

  when(manager.getTableRows("teachers")).thenReturn(Arrays.asList(set));

    command.process("find|teachers");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(view, atLeastOnce()).write(captor.capture());
    assertEquals("[--------------------------, "+
                "|id|surname|subject|city|, "+
                "--------------------------, "+
                "|1|Petrov|History|Lviv|, "+
                "--------------------]", captor.getAllValues().toString());

    }


}