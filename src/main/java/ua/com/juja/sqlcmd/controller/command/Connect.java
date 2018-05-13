package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Connect extends DataClass implements Command {
    private static String COMMAND = "connect|Academy|postgres|1401198n";
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

        List<String> data = getTableData(command);
        if (data.size() != parametersLength()) {
           view.write("Error entering command, should be 'connect|database|username|password'");
        return;
        }
        String databaseName = getTableName(data);
        String userName = data.get(2);
        String password = data.get(3);
        try {
            manager.connect(databaseName, userName, password);
            view.write(String.format("You have login to database '%s' successfully!", databaseName));
        } catch (SQLException e) {
            view.write(String.format("The connection to database '%s' for user '%s' is failed due to'%s'", databaseName, userName, e.getMessage()));
        }
    }
    private int parametersLength() {
        return COMMAND.split(SEPARATOR).length;
    }
}