package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.regex.Pattern;

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

        String[] data = command.split(SEPARATOR);
        if (data.length < 6 || data.length % 2 == 1) {
            view.write(String.format("Error entering command '%s'. Should be 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN", command));
            return;
        }
        String tableName = data[1];
        String primaryKey = data[2];
        DataSet set = new DataSet();
        for (int i = 2; i < data.length; i++) {
            set.put(data[i], data[++i]);
        }

        try {
            manager.insert(tableName, set, primaryKey);
            view.write(String.format("Statement are added into the table '%s'", tableName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}