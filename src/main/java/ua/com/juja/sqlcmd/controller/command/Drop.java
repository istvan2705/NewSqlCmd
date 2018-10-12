package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Drop  implements Command {

    private DataSet data;
    private DatabaseManager manager;
    private View view;

    public Drop(DataSet data, DatabaseManager manager, View view) {
        this.data = data;
        this.manager = manager;
        this.view = view;
    }

    @Override
    public String getStatusProcess() {
        List<String> parameters = data.getParameters();
        if (parameters.size() != 2) {
            return String.format(ERROR_ENTERING_MESSAGE + "'drop|tableName'");

        }
        String tableName = data.getTableName();

        try {
            manager.deleteTable(tableName);
            return String.format("The table '%s' has been deleted", tableName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}
