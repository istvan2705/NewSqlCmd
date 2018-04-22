package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Drop implements Command {
   private DatabaseManager manager;
   private View view;

    public Drop(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("drop|");
    }

    @Override
    public void process(String command) {
        try {

            manager.deleteTable(command);
            view.write("The table has been deleted");
        }
        catch(SQLException e){
            view.write(String.format("Can not execute command  due to: %s", e.getMessage()));
        }
    }
    }
