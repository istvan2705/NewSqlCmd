package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;


public class Update implements Command {

    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) throws DBConnectionException {
        this.manager = manager;
        this.view = view;
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
    }

    @Override
    public void execute() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters < 6 || numberOfParameters % 2 == 1) {
            view.write( ERROR_ENTERING_MESSAGE + "'update|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN'");
        }
        String tableName = InputWrapper.getTableName();
        List<String> values = InputWrapper.getTableData();
        List<String> columns = InputWrapper.getColumns();
        List<Object> rows = InputWrapper.getRows();
        String keyColumn = values.get(0);
        String keyValue = values.get(1);

        try {
            manager.update(tableName, columns, rows, keyColumn, keyValue);
            view.write( "The row has been updated");
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}
