package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Drop implements Command {
    CommandParser commandParser = new CommandParser();
    private DatabaseManager manager;
    private View view;

    public Drop(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void execute(String command) throws SQLException {
        int numberOfParameters = commandParser.getNumberOfParameters(command);
        if (numberOfParameters != 2) {
            view.write(ERROR_ENTERING_MESSAGE + "'drop|tableName'");
            return;
        }
        String tableName = commandParser.getTableName(command);
        manager.deleteTable(tableName);
        view.write(String.format("The table '%s' has been deleted", tableName));
    }
}
