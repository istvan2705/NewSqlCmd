package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;

import java.sql.SQLException;
import java.util.List;

public class Connect implements Command {


    private DatabaseManager manager;

    public Connect(DatabaseManager manager) {
          this.manager = manager;
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters != 4) {
            return ERROR_ENTERING_MESSAGE + "'connect|database|username|password'";
        }
        String databaseName = InputWrapper.getTableName();
        List<String> values = InputWrapper.getTableData();
        String userName = values.get(0);
        String password = values.get(1);
        try {
            manager.connect(databaseName, userName, password);
            return String.format("You have connected to database '%s' successfully!", databaseName);
        } catch (SQLException e) {
            return String.format("The connection to database '%s' for user '%s' is failed due to'%s'", databaseName, userName, e.getMessage());
        }
    }
}