package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Drop extends DataClass implements Command {
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
        List<String> data = getTableData(command);
        if (data.size() != 2) {
            view.write(String.format("Error entering command '%s', it should be'drop|tableName", command));
            return;
        }
        String tableName = getTableName(data);
        try {
            manager.deleteTable(tableName);
            view.write(String.format("The table '%s' has been deleted", tableName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}
