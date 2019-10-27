package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Delete implements Command {
    private CommandParser commandParser = new CommandParser();
    private DatabaseManager manager;
    private View view;

    public Delete(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void execute(String command) throws SQLException {
        int numberOfParameters = commandParser.getNumberOfParameters(command);
        if (numberOfParameters < 4 || numberOfParameters % 2 == 1) {
            view.write(ERROR_ENTERING_MESSAGE + "'delete|tableName|column|value'");
            return;
        }
        String tableName = commandParser.getTableName(command);
        List<String> values = commandParser.getParameters(command).stream().skip(2).collect(Collectors.toList());
        String columnName = values.get(0);
        String rowName = values.get(1);
        boolean isRowDeleted = manager.deleteRows(tableName, columnName, rowName);
        if (isRowDeleted) {
            view.write("The row has been deleted");
        } else view.write("The row " + columnName +" does not exist");
    }
}




