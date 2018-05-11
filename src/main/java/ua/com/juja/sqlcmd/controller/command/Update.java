package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update implements Command {

    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");

        if (data.length < 6 || data.length % 2 == 1) {
            view.write(String.format("Error entering command '%s'. Should be 'update|tableName|column1|value1|column2|value2|...|columnN|valueN", command));
            return;
        }
        String tableName = data[1];
        DataSet set = new DataSet();
        for (int i = 2; i < data.length - 1; i++) {
            set.put(data[i], data[++i]);
        }
        String id = data[3];

        try {
            manager.update(tableName, id, set);
            view.write("The row has been updated");
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));

        }
    }
}
