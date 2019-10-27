package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.controller.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Update implements Command {
    private CommandParser commandParser = new CommandParser();
    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void execute(String command) throws SQLException {
        int numberOfParameters = commandParser.getNumberOfParameters(command);
        if (numberOfParameters < 6 || numberOfParameters % 2 == 1) {
            view.write(ERROR_ENTERING_MESSAGE + "'update|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN'");
            return;
        }
        String tableName = commandParser.getTableName(command);
        List<String> columns = commandParser.getColumns(command);
        List<String> rows = commandParser.getRows(command);
        String keyColumn = columns.get(0);
        String keyValue = rows.get(0);
        String column = columns.get(1);
        String value = rows.get(1);
        boolean isUpdate = manager.update(tableName, keyColumn, keyValue, column, value);
        if (isUpdate) {
            view.write("The row has been updated");
        } else view.write("The row has been not updated, because it does not exist");
    }
}
