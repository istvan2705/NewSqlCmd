package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;

import java.sql.SQLException;
import java.util.*;

public class Insert implements Command {
    private DatabaseManager manager;

     public Insert( DatabaseManager manager) throws DBConnectionException {
        this.manager = manager;
         if (!manager.isConnected()){
             throw new DBConnectionException();
         }
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters < 6 || numberOfParameters % 2 == 1) {
            return "ERROR_ENTERING_MESSAGE" + "'insert|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN";
        }
        String tableName = InputWrapper.getTableName();
        List<String> columns = InputWrapper.getColumns();
        List<Object> rows = InputWrapper.getRows();
        try {
            manager.insert(tableName, columns, rows);
            return String.format("Statement are added into the table '%s'", tableName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}