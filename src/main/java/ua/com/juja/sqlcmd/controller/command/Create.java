package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Create implements Command {
    private DatabaseManager manager;
    private View view;
    private DataSet data = new DataSet();

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
        List<String> parameters = data.getParameters(command);
        if (parameters.size() < 4) {
            view.write(String.format("Error entering command '%s'. Should be 'create|tableName|column1|column2|" +
                    "...|columnN", command));
            return;
        }
        String tableName = data.getTableName(command);
        List<String>values = data.getDataTable(command);

        try {
            manager.create(tableName, values);
            view.write(String.format("The table '%s' has been created", tableName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}
