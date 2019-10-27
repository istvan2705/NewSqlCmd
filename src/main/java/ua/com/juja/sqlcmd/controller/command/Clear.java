package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Clear implements Command {
    private CommandParser commandParser = new CommandParser();
    private DatabaseManager manager;
    private View view;

    public Clear(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void execute(String command) throws SQLException {
        int numberOfParameters = commandParser.getNumberOfParameters(command);
        if (numberOfParameters != 2) {
            view.write(ERROR_ENTERING_MESSAGE + "'clear|tableName'");
            return;
        }
        String tableName = commandParser.getTableName(command);
        boolean isContentDeleted = manager.clear(tableName);
        if (isContentDeleted) {
            view.write(String.format("The content of table '%s' has been deleted", tableName));
        } else {
            view.write("You are trying to clear the contents of an empty table");
        }
    }
}