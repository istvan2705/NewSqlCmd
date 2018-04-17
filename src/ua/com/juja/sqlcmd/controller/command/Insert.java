package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class Insert implements Command {
    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        try {
            String tableName = data[1];
            String constraint = data[2];
            if (manager.tableExist(tableName)){
                manager.insert(tableName, data, constraint);
            }
            else{
                view.write("The table " + tableName + " does not exist. Please enter existing");

            }
        }catch (IndexOutOfBoundsException e) {
            view.write("You have not entered all needed parameters or names of parameters are not correct.");
        }
    }
}