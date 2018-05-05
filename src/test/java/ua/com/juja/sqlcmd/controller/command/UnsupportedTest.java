package ua.com.juja.sqlcmd.controller.command;
import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.view.View;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;


public class UnsupportedTest {
    private View view;
    private Command command;

    @Before
    public void init() {

        view = mock(View.class);
        command = new Unsupported(view);
    }


    @Test
    public void testUnsupportedCanProcessError() {
        assertTrue(command.canProcess("insert|"));
    }


}