package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Clear implements Command {

    private DatabaseManager manager;
    private View view;

    public Clear(DatabaseManager manager, View view) throws DBConnectionException {
        this.manager = manager;
        this.view = view;
        if (!manager.isConnected()) {
            throw new DBConnectionException();
             }
        }


    @Override
    public void execute() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters != 2) {
             view.write(ERROR_ENTERING_MESSAGE + "'clear|tableName'");
        }
        String tableName = InputWrapper.getTableName();
        try {
            boolean isCleared = manager.clear(tableName);
            if (isCleared) {
                view.write(String.format("The content of table '%s' has been deleted", tableName));
            } else {
                view.write("You are trying to clear the contents of an empty table");
            }
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));

        }
    }
}