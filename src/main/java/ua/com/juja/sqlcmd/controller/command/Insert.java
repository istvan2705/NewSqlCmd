package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.*;

public class Insert implements Command {
    private DatabaseManager manager;
    private View view;

     public Insert( DatabaseManager manager, View view) {
         this.view = view;
        this.manager = manager;
       }

    @Override
    public void execute(String command) {
        int numberOfParameters = InputWrapper.getNumberOfParameters(command);
        if (numberOfParameters < 6 || numberOfParameters % 2 == 1) {
            view.write("ERROR_ENTERING_MESSAGE" + "'insert|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN");
        }
        String tableName = InputWrapper.getTableName(command);
        List<String> columns = InputWrapper.getColumns(command);
        List<Object> rows = InputWrapper.getRows(command);
        try {
            manager.insert(tableName, columns, rows);
            view.write(String.format("Statement are added into the table '%s'", tableName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}