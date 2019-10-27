package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Set;

public class Tables implements Command {
    CommandParser commandParser = new CommandParser();
    private DatabaseManager manager;
    private View view;

    public Tables(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        }

    @Override
    public void execute(String command) throws SQLException {
        int numberOfParameters = commandParser.getNumberOfParameters(command);
        if (numberOfParameters != 1) {
            view.write(ERROR_ENTERING_MESSAGE + "'tables'");
            return;
        }
         view.write (format(manager.getTableNames()));
       }
    private String format(Set<String> tables) {
        return tables.toString();
    }
}