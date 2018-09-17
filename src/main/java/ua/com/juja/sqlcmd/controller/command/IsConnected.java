package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class IsConnected implements Command {
    private DatabaseManager manager;
    private View view;

    public IsConnected(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

       @Override
    public void process(String command) {
        view.write(String.format("You can not use command '%s' until " + "you have established connection to the database "+
                "connect|databaseName|userName|password", command));
    }
}