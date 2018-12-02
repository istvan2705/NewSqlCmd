package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Delete implements Command {

    private DatabaseManager manager;
    private View view;
    public Delete(DatabaseManager manager,View view) {
          this.manager = manager;
          this.view = view;
            }

    @Override
    public void execute(String command) {
        int numberOfParameters = InputWrapper.getNumberOfParameters(command);
        if (numberOfParameters < 4 || numberOfParameters % 2 == 1) {
            view.write(ERROR_ENTERING_MESSAGE + "'delete|tableName|column|value'");
            return;
        }
        String tableName = InputWrapper.getTableName(command);
        List<String> values = InputWrapper.getTableData(command);
        String columnName = values.get(0);
        String rowValue = values.get(1);
        try {
            boolean isDeleted = manager.deleteRows(tableName, columnName, rowValue);
            if (isDeleted) {
               view.write( "The row has been deleted");
            } else
                view.write(String.format("Error entering command. The row value '%s' does not exist", rowValue));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}

