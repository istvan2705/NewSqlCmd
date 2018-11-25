package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.*;

public class Insert implements Command {
    private DatabaseManager manager;
    private View view;

     public Insert( DatabaseManager manager, View view) throws DBConnectionException {
         this.view = view;
        this.manager = manager;
         if (!manager.isConnected()){
             throw new DBConnectionException();
         }
    }

    @Override
    public void execute() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters < 6 || numberOfParameters % 2 == 1) {
            view.write("ERROR_ENTERING_MESSAGE" + "'insert|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN");
        }
        String tableName = InputWrapper.getTableName();
        List<String> columns = InputWrapper.getColumns();
        List<Object> rows = InputWrapper.getRows();
        try {
            manager.insert(tableName, columns, rows);
            view.write(String.format("Statement are added into the table '%s'", tableName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}