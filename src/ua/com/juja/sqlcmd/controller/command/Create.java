package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class Create implements Command {
  private  DatabaseManager manager;
  private View view;

    public Create(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");

        try {
        String tableName = data[1];
        DataSet columns = new DataSet();
        for (int i = 2; i < data.length; i++) {
            columns.put(data[i], i);
        }

            if (manager.tableExist(tableName)) {
                view.write("The table " + tableName + " is already exists. Please enter new name of table");
            }
            else{
                manager.create(columns, tableName);
               view.write("Table has been created");
            }

        }catch (IndexOutOfBoundsException e) {
            view.write("Error entering command, should be like create|tableName");
        }

    }
}