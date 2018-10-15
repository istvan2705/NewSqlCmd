package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputSet;

import java.sql.SQLException;

public class Drop implements Command {

    private InputSet inputSet;
    private DatabaseManager manager;

    public Drop(InputSet inputSet, DatabaseManager manager) {
        this.inputSet = inputSet;
        this.manager = manager;
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = inputSet.getNumberOfParameters();
        if (numberOfParameters != 2) {
            return ERROR_ENTERING_MESSAGE + "'drop|tableName'";

        }
        String tableName = inputSet.getTableName();

        try {
            manager.deleteTable(tableName);
            return String.format("The table '%s' has been deleted", tableName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}
