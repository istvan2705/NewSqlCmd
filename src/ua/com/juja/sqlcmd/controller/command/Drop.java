package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

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
            String[] data = command.split("\\|");
            String tableName = data[1];

            manager.deleteTable(tableName);
            view.write("The table has been deleted");
        }
        catch(ArrayIndexOutOfBoundsException e)
        {  view.write("Error entering command, should be like drop|tableName");
        }
    }
    }
