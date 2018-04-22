package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static org.junit.Assert.assertEquals;

public class FindTest {
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup(){
    manager = mock(DatabaseManager.class);
    view = mock(View.class);


    }

    @Test
    public void testFind(){

    Command command = new Find(manager, view);
   when(manager.getColumnsNames("teachers")).thenReturn(new String[] {"id", "surname", "subject", "city"} );

 //connect|Academy|postgres|1401198n when(manager.getTableRows("teachers")).thenReturn(new String[]{"1", "Petrov", "Math", "Lviv"});

    command.process("find|teachers");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(view, atLeastOnce()).write(captor.capture());
    assertEquals(
    "[--------------------------, "+
            "|id|surname|subject|city|, "+
            "--------------------------, "+
            "|1|Petrov|Math|Lviv|]",captor.getAllValues().toString());
    }


}