package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;

import java.sql.SQLException;
import java.util.List;

public class Create implements Command {


    private DatabaseManager manager;

    public Create(DatabaseManager manager) {
        this.manager = manager;
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters < 4) {
            return ERROR_ENTERING_MESSAGE + "'create|tableName|column1|column2|...|columnN'";
        }
        String tableName = InputWrapper.getTableName();
        List<String> values = InputWrapper.getTableData();

        try {
            manager.create(tableName, values);
            return String.format("The table '%s' has been created", tableName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}
