package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeleteTest {

        private View view;
        private DatabaseManager manager;
        private Command command;

        @Before
        public void init() {
            manager = mock(DatabaseManager.class);
            view = mock(View.class);
            command = new Delete(manager, view);
        }

        @Test
        public void testDeleteCanProcess() {
            assertTrue(command.canProcess("delete|teachers|subject|Math"));
        }

        @Test
        public void testDeleteCanProcessError() {
            assertFalse(command.canProcess("delete"));
        }


        @Test
        public void testDelete() throws SQLException{
            String tableName = "teachers";
            String columnName = "subject";
            String rowName = "Math";

            command.process("delete|teachers|subject|Math");

            verify(manager).deleteRows(tableName,columnName, rowName);
            verify(view).write( "The row has been deleted");
        }

    }