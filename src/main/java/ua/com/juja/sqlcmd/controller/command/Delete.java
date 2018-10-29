package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;

import java.sql.SQLException;
import java.util.List;

public class Delete implements Command {

    private DatabaseManager manager;

    public Delete(DatabaseManager manager) throws DBConnectionException {
        this.manager = manager;
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters < 4 || numberOfParameters % 2 == 1) {
            return ERROR_ENTERING_MESSAGE + "'delete|tableName|column|value'";
        }
        String tableName = InputWrapper.getTableName();
        List<String> values = InputWrapper.getTableData();
        String columnName = values.get(0);
        String rowName = values.get(1);
        try {
            boolean isDeleted = manager.deleteRows(tableName, columnName, rowName);
            if (isDeleted) {
                return "The row has been deleted";
            } else
                return String.format("Error entering command. The row with rowName '%s' does not exist", rowName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }

    }
}

