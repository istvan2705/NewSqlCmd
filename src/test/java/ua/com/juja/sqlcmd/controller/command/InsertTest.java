package ua.com.juja.sqlcmd.controller.command;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.*;

public class InsertTest {

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
        command = new Insert(manager, view);
    }


    @Test
    public void testInsertIfRowNotExists() throws SQLException  {
        String tableName = "teachers";
        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("surname");
        columns.add("name");
        columns.add("subject");

        List<Object> rows = new ArrayList<>();
        rows.add("3");
        rows.add("Ivanov");
        rows.add("History");
        rows.add("Lviv");

        command.execute();
        verify(manager).insert(tableName, columns, rows);
    }


    @Test
    public void testInsertIfRowExists() throws SQLException{
        String tableName = "teachers";
        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("surname");
        columns.add("name");
        columns.add("subject");

        List<Object> rows = new ArrayList<>();
        rows.add("3");
        rows.add("Ivanov");
        rows.add("History");
        rows.add("Lviv");


        command.execute();
        verify(manager).insert(tableName, columns, rows);
            command.execute();
            verify(manager).insert(tableName, columns, rows);
            verify(view).write(String.format("Statement are added into the table '%s'", tableName));
        }
}