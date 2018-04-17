package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

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
            throw new IllegalArgumentException(String.format("You have entered not correct quantity of parameters, separated by symbol '|', waiting %s, and there is: %s ", parametersLength(), data.length));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        manager.connect(databaseName, userName, password);
        view.write("You have login successfully!");

    }

    private int parametersLength() {
        return COMMAND.split("\\|").length;
    }


}