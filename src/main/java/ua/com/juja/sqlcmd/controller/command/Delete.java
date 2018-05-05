package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Delete implements Command {
   private DatabaseManager manager;
   private View view;

    public Delete(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void process(String command) {

        try {
            String[] data = command.split("\\|");

            if (data.length != 4) {
                view.write(String.format("Erorr entering command '%s'. Should be delete|tableName|column|value", command));
                return;
            }
            String tableName = data[1];
            String columnName = data[2];
            String rowName = data[3];

            boolean isDeleted = manager.deleteRows(tableName, columnName, rowName);
            if (isDeleted) {

                view.write("The row has been deleted");

            } else  view.write(String.format("Error entering command. The row with rowName  '%s' does not exist", rowName));
        }

          catch (SQLException e) {
            view.write(String.format("Can not execute command  due to: %s", e.getMessage()));
        }




    }

}