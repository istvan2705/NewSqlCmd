package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;


public class Clear  implements Command {

    private DataSet data;
    private DatabaseManager manager;
    private View view;

    public Clear(DataSet data, DatabaseManager manager, View view) {
        this.data = data;
        this.manager = manager;
        this.view = view;
    }

    @Override
    public String getStatusProcess() {
        List<String> parameters = data.getParameters();
        if (parameters.size() != 2) {
            return ERROR_ENTERING_MESSAGE + "'clear|tableName'";
            }
        String tableName = data.getTableName();
        try {
            boolean isCleared = manager.clear(tableName);
            if (isCleared) {
                return String.format("The content of table '%s' has been deleted", tableName);
           }
           else {
                return "You are trying to clear the contents of an empty table";
            }
        } catch (SQLException e) {
           return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());

        }
    }
}