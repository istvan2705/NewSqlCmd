package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UpdateTest {


        private DatabaseManager manager;
        private View view;
        private Command command;

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
        public void testUpdate() throws SQLException{
            String tableName = "teachers";
            DataSet set = new DataSet();
            set.put("id", "3");
            set.put("surname", "Bogdanov");

            String id = "3";

            command.process("update|teachers|id|3|surname|Bogdanov");

            verify(manager).update(eq(tableName), eq(id), any(DataSet.class));
            verify(view).write("The row has been updated");
        }



    }