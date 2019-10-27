package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Connect implements Command {
    private CommandParser commandParser = new CommandParser();
    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
         this.view =view;
        }

    @Override
    public void execute(String command) {
        int numberOfParameters = commandParser.getNumberOfParameters(command);
        if (numberOfParameters != 4) {
            view.write(ERROR_ENTERING_MESSAGE + "'connect|database|username|password'");

        }
        String databaseName = commandParser.getTableName(command);
        List<String> values = commandParser.getParameters(command).stream().skip(2).collect(Collectors.toList());
        String userName = values.get(0);
        String password = values.get(1);
        try {
            manager.connect(databaseName, userName, password);
           view.write(String.format("You have connected to database '%s' successfully!", databaseName));
        } catch (SQLException e) {
            view.write(String.format("The connection to database '%s' for user '%s' is failed due to'%s'", databaseName, userName, e.getMessage()));
        }
    }
}