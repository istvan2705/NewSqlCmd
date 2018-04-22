package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Connect implements Command {
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

        String[] data = command.split("\\|");
        if (data.length != parametersLength()) {
            throw new IllegalArgumentException("Error entering command, should be 'connect|database|username|password'");
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];
        try {
            manager.connect(databaseName, userName, password);
            view.write(String.format("You have login to database '%s' successfully!", databaseName));
        } catch (SQLException e) {
            view.write(String.format("The connection to database '%s' for user '%s' is failed due to'%s'", databaseName, userName, e.getMessage()));
        }
    }
    private int parametersLength() {
        return COMMAND.split("\\|").length;
    }


}