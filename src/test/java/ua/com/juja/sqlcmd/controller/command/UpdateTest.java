package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;



public class UpdateTest {

    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
        command = new Update(manager, view);
    }


    @Test
    public void testUpdateIfRowExist() throws SQLException {
        String tableName = "teachers";
        String keyColumn = "surname";
        String keyValue = "Ivanov";

        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("surname");
        columns.add("name");
        columns.add("subject");

        List<Object> rows = new ArrayList<>();
        rows.add("3");
        rows.add("Ivanov");
        rows.add("Ivan");
        rows.add("history");

        manager.update(tableName, columns,rows, keyColumn, keyValue);

        command.execute();
        verify(manager).update(tableName, columns,rows, keyColumn, keyValue);
       verify(view).write("The row has been updated");
            }

    @Test
    public void testUpdateIfRowNotExist() throws SQLException {
        String tableName = "teachers";
        String keyColumn = "surname";
        String keyValue = "Ivanov";

        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("surname");
        columns.add("name");
        columns.add("subject");

        List<Object> rows = new ArrayList<>();
        rows.add("3");
        rows.add("Ivanov");
        rows.add("Ivan");
        rows.add("history");

        manager.update(tableName, columns,rows, keyColumn, keyValue);
        command.execute();
        verify(manager).update(tableName, columns,rows, keyColumn, keyValue);
        verify(view).write("The row has been not updated due to incorrect parameter");
         }
       }

