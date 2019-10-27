package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Insert implements Command {
    private CommandParser commandParser = new CommandParser();
    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public void execute(String command) throws SQLException {
        int numberOfParameters = commandParser.getNumberOfParameters(command);
        if (numberOfParameters < 6 || numberOfParameters % 2 == 1) {
            view.write(ERROR_ENTERING_MESSAGE + " 'insert|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN");
            return;
        }
        String tableName = commandParser.getTableName(command);
        List<String> columns = commandParser.getColumns(command);
        List<String> rows = commandParser.getRows(command);
        manager.insert(tableName, columns, rows);
        view.write(String.format("Statement are added into the table '%s'", tableName));
    }
}