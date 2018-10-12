package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Connect implements Command {

    private DataSet data;
    private DatabaseManager manager;
    private View view;

    public Connect(DataSet data, DatabaseManager manager, View view) {
        this.data = data;
        this.manager = manager;
        this.view = view;
    }

     @Override
     public String getStatusProcess() {
        List<String> parameters = data.getParameters();
        if (parameters.size() != 4) {
            return ERROR_ENTERING_MESSAGE + "'connect|database|username|password'";
           }
        String databaseName = data.getTableName();
        List<String> values = data.getTableData();
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