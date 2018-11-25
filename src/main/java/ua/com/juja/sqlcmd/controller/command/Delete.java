package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Delete implements Command {

    private DatabaseManager manager;
    private View view;
    public Delete(DatabaseManager manager,View view) throws DBConnectionException {
          this.manager = manager;
          this.view = view;
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
    }

    @Override
    public void execute() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters < 4 || numberOfParameters % 2 == 1) {
            view.write(ERROR_ENTERING_MESSAGE + "'delete|tableName|column|value'");
        }
        String tableName = InputWrapper.getTableName();
        List<String> values = InputWrapper.getTableData();
        String columnName = values.get(0);
        String rowValue = values.get(1);
        try {
            boolean isDeleted = manager.deleteRows(tableName, columnName, rowValue);
            if (isDeleted) {
               view.write( "The row has been deleted");
            } else
                view.write(String.format("Error entering DatabaseManagerMockitoTest. The row value '%s' does not exist", rowValue));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}

