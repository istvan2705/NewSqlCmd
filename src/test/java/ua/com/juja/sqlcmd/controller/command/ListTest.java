package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;


public class ListTest {
private DatabaseManager manager;
private View view;

@Before
    public void setup(){
    manager = mock(DatabaseManager.class);
    view = mock(View.class);

}
@Test
 public void testListTables() throws SQLException{
    Command command = new Tables(manager, view);
    when(manager.getTableNames()).thenReturn(new LinkedHashSet<>(Arrays.asList("teachers", "students")));

    command.process("list");

   ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(view, atLeastOnce()).write(captor.capture());



    assertEquals("[[teachers, students]]", captor.getAllValues().toString());


}


} 