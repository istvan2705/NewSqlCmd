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


public class CreateTest  {
    public View view;
    public DatabaseManager manager;
    public Command command;

    @Before
    public void init() throws DBConnectionException{
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
        command = new Create(manager, view);
    }

       @Test
    public void testCreateIfNotExists() throws SQLException {
        String tableName = "students";
       List<String> list = new ArrayList<>();
       list.add("id");
       list.add("surname");
       list.add("name");
      manager.create(tableName,list);

        command.execute();

        verify(manager).create(tableName,list);
        verify(view).write(String.format("The table '%s' has been created", tableName));
    }

    @Test
    public void testNotCreateIfExists() throws SQLException {
        String tableName = "students";
        List<String> list = new ArrayList<>();
        list.add("id");
        list.add("surname");
        list.add("name");
        manager.create(tableName,list);

       command.execute();

       verify(manager).create(tableName, list);
            verify(view).write(String.format("The table '%s' has been created", tableName));
        }

    }


