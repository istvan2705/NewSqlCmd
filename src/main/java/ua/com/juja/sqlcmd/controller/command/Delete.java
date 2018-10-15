package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputSet;

import java.sql.SQLException;
import java.util.List;

public class Delete implements Command {

    private InputSet inputSet;
    private DatabaseManager manager;

    public Delete(InputSet inputSet, DatabaseManager manager) {
        this.inputSet = inputSet;
        this.manager = manager;
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = inputSet.getNumberOfParameters();
        if (numberOfParameters < 4 || numberOfParameters % 2 == 1) {
            return ERROR_ENTERING_MESSAGE + "'delete|tableName|column|value'";
        }
        String tableName = inputSet.getTableName();
        List<String> values = inputSet.getTableData();
        String columnName = values.get(0);
        String rowName = values.get(1);
        try {
            boolean isDeleted = manager.deleteRows(tableName, columnName, rowName);
            if (isDeleted) {
                return "The row has been deleted";
            } else
                return String.format("Error entering command. The row with rowName  '%s' does not exist", rowName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }

    }
}

