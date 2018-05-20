package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Connect extends DataClass implements Command {
    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
        List<String> values = getDataTable(command);
        if (values.size() != 2) {
            view.write("Error entering command, should be 'connect|database|username|password'");
            return;
        }
        String databaseName = getTableName(command);
        String userName = values.get(0);
        String password = values.get(1);
        try {
            manager.connect(databaseName, userName, password);
            view.write(String.format("You have login to database '%s' successfully!", databaseName));
        } catch (SQLException e) {
            view.write(String.format("The connection to database '%s' for user '%s' is failed due to'%s'", databaseName, userName, e.getMessage()));
        }
    }
}