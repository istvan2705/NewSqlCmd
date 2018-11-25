package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.Connection;
import java.sql.SQLException;

public class Clear implements Command {

    private DatabaseManager manager;
    private View view;

        public Clear(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        }

    @Override
    public void execute(String command) {
        int numberOfParameters = InputWrapper.getNumberOfParameters(command);
        if (numberOfParameters != 2) {
             view.write(ERROR_ENTERING_MESSAGE + "'clear|tableName'");
        }
        String tableName = InputWrapper.getTableName(command);
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