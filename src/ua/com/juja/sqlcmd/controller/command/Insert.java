package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
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
            DataSet set = new DataSet();
            for (int i = 2; i < data.length; i++) {
                set.put(data[i], data[++i]);
            }
            String tableName = data[1];
            String constraint = data[2];
            if (manager.tableExist(tableName)) {
                manager.insert(tableName, set, constraint);

            }
            else {
              view.write(String.format("The second parameter '%s' has been entered not correctly", tableName));

            }
        }    catch(IndexOutOfBoundsException e){
                    view.write("Error entering command, it should be like insert|tableName|column1|value1");
                }
            }

    }
