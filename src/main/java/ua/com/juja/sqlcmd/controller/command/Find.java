package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;

import java.sql.SQLException;
import java.util.Set;

public class Find implements Command {

    private DatabaseManager manager;

    public Find(DatabaseManager manager) throws DBConnectionException {
        this.manager = manager;
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters != 2) {
            return ERROR_ENTERING_MESSAGE + "'find|tableName'";
        }
        String tableName = InputWrapper.getTableName();
        try {
            Set<String> columns = manager.getColumnsNames(tableName);
            String tableHeader = printColumnsNames(columns);
            manager.getTableRows(tableName);
              return tableHeader;
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }

    private String printColumnsNames(Set<String> columns) {
        StringBuilder result = new StringBuilder();
        for (String column : columns) {
            result.append("|").append(column).append("|");
        }
        return "--------------------------" + "\n" +
                result.toString() + "\n" +
                "--------------------------" +"\n";
    }
}



