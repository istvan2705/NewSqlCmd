package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputSet;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Create implements Command {

    private InputSet inputSet;
    private DatabaseManager manager;

    public Create (InputSet inputSet, DatabaseManager manager) {
        this.inputSet = inputSet;
        this.manager = manager;
    }


    @Override
    public String getStatusProcess() {
      int numberOfParameters  = inputSet.getNumberOfParameters();
        if (numberOfParameters < 4) {
            return String.format(ERROR_ENTERING_MESSAGE + "'create|tableName|column1|column2|" +
                    "...|columnN'");

        }
        String tableName = inputSet.getTableName();
        List<String>values = inputSet.getTableData();

        try {
            manager.create(tableName, values);
            return String.format("The table '%s' has been created", tableName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}
