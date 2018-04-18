package ua.com.juja.sqlcmd.controller.command;

import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

public class ExitTest {
    private ControlView view = new ControlView();

@Test
public void testCanProcessExit(){
    //given
  Command command = new Exit(view);

  //when
boolean canProcess = command.canProcess("exit");

//then
  assertTrue(canProcess);

}
 @Test
 public void testCantProcessExit(){
     Command command = new Exit(view);

     boolean canProcess = command.canProcess("tree");

     assertFalse(canProcess);

    }

    @Test
    public void testProcessExit_throwsExitException(){
        Command command = new Exit(view);
     try {
         command.process("exit");
         fail("Excepted ExitException");
     }catch(ExitException e){
         //do nothing
     }

        assertEquals("See you soon!\n", view.getContent());
  }

}

