package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Set;

public class Tables implements Command {

    private DatabaseManager manager;
    private View view;

    public Tables(DatabaseManager manager, View view) throws DBConnectionException {
        this.manager = manager;
        this.view = view;
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
    }

    @Override
    public void execute() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters != 1) {
            view.write(ERROR_ENTERING_MESSAGE + "'tables'");
        }

        try {
            view.write (format(manager.getTableNames()));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }

    private String format(Set<String> tables) {
        return tables.toString();
    }
}