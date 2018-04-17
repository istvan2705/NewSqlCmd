package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.util.Arrays;


public class ListTest {
private DatabaseManager manager;
private View view;

@Before
    public void setup(){
    manager = mock(DatabaseManager.class);
    view = mock(View.class);

}
@Test
 public void testListTables(){
    Command command = new List(manager, view);
    when(manager.getTableNames()).thenReturn(new String[]{"teachers", "students"});

    command.process("list");

   ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(view, atLeastOnce()).write(captor.capture());



    assertEquals("[[teachers, students]]", captor.getAllValues().toString());


}


} 