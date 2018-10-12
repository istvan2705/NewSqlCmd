package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Delete implements Command {

    private DataSet data;
    private DatabaseManager manager;
    private View view;

    public Delete(DataSet data, DatabaseManager manager, View view) {
        this.data = data;
        this.manager = manager;
        this.view = view;
    }

    @Override
    public String getStatusProcess() {
        List<String> parameters = data.getParameters();
        if (parameters.size() < 4 || parameters.size() % 2 == 1) {
            return String.format(ERROR_ENTERING_MESSAGE + "'delete|tableName|column|value'");
        }
        String tableName = data.getTableName();
        List<String> values = data.getTableData();
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

