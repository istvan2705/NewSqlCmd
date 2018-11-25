package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Drop implements Command {

    private DatabaseManager manager;
    private View view;
    public Drop(DatabaseManager manager, View view) throws DBConnectionException {
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
            view.write(ERROR_ENTERING_MESSAGE + "'drop|tableName'");
        }
        String tableName = InputWrapper.getTableName();

        try {
            manager.deleteTable(tableName);
           view.write(String.format("The table '%s' has been deleted", tableName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}
