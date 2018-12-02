package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;


public class Update implements Command {

    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view)  {
        this.manager = manager;
        this.view = view;
        }

    @Override
    public void execute(String command) {
        int numberOfParameters = InputWrapper.getNumberOfParameters(command);
        if (numberOfParameters < 6 || numberOfParameters % 2 == 1) {
            view.write( ERROR_ENTERING_MESSAGE + "'update|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN'");
            return;
        }
        String tableName = InputWrapper.getTableName(command);
        List<String> values = InputWrapper.getTableData(command);
        List<String> columns = InputWrapper.getColumns(command);
        List<Object> rows = InputWrapper.getRows(command);
        String keyColumn = values.get(0);
        String keyValue = values.get(1);

        try {
            manager.update(tableName, columns, rows, keyColumn, keyValue);
            view.write("The row has been updated");
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}
