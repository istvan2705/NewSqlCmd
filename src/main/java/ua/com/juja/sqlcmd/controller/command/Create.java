package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Create implements Command {
    private DatabaseManager manager;
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

        try {
            String[] data = command.split("\\|");
            if (data.length < 4) {
                view.write(String.format("Error entering command '%s'. Should be 'create|tableName|column1|column2|...|columnN", command));
                return;
            }

            String tableName = data[1];
            DataSet columns = new DataSet();
            for (int i = 2; i < data.length; i++) {
                columns.put(data[i], i);
            }

            boolean isCreated = manager.create(tableName,columns);
            if (!isCreated) {

            view.write(String.format("The table '%s' has been created", tableName));
        } else {
            view.write(String.format("The table '%s' already exist", tableName));

        }
        } catch (SQLException e) {
            view.write(String.format("Can not execute command  due to: %s", e.getMessage()));
        }




}
}
