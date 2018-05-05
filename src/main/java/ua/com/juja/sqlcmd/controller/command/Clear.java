package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Clear implements Command {
    private DatabaseManager manager;
    private View view;

    public Clear(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {

        String [] data = command.split("\\|");
        if (data.length != 2) {
            view.write(String.format("Error entering command '%s', it should be'clear|tableName", command));
            return;
        }

        String tableName = data[1];

        try {
            boolean isCleared = manager.clear(tableName);
            if (isCleared) {

                view.write(String.format("The content of table '%s' has been deleted", tableName));
            }
            else{
                view.write("You are trying to clear the contents of an empty table");
            }

        }
        catch (SQLException e) {
            view.write(String.format("Can not execute command  due to: %s", e.getMessage()));

        }
    }
}