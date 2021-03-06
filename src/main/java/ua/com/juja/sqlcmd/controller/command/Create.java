package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Create implements Command {
    private CommandParser commandParser = new CommandParser();
    private DatabaseManager manager;
    private View view;

    public Create(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void execute(String command) throws SQLException {
        int numberOfParameters = commandParser.getNumberOfParameters(command);
        if (numberOfParameters < 3) {
            view.write(ERROR_ENTERING_MESSAGE + "'create|tableName|column1|column2|...|columnN'");
            return;
        }
        String tableName = commandParser.getTableName(command);
        List<String> columns = commandParser.getParameters(command).stream().skip(2).collect(Collectors.toList());
        manager.create(tableName, columns);
        view.write(String.format("The table '%s' has been created", tableName));
    }
}

