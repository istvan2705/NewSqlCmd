package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Find implements Command {
    private CommandParser commandParser = new CommandParser();
    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void execute(String command) throws SQLException {
        int numberOfParameters = commandParser.getNumberOfParameters(command);
        if (numberOfParameters != 2) {
            view.write(ERROR_ENTERING_MESSAGE + "'find|tableName'");
            return;
        }
        String tableName = commandParser.getTableName(command);
        Set<String> columns = manager.getColumnsNames(tableName);
        printColumnsNames(columns);
        List<String> rows = manager.getTableRows(tableName);
        printTableRows(rows);
    }

    private void printColumnsNames(Set<String> columns) {
        StringBuilder result = new StringBuilder();
        for (String column : columns) {
            result.append("|").append(column);
        }
        view.write("--------------------------" + "\n" +
                result.toString() + "\n" +
                "--------------------------");
    }

    private void printTableRows(List<String> rows) {
        StringBuilder result = new StringBuilder();
        for (String row : rows) {
            result.append("|").append(row);
        }
        view.write(result.toString() +
                "--------------------------");
    }
}



