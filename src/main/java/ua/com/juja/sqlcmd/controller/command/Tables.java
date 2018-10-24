package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.SQLException;
import java.util.Set;

public class Tables implements Command {

    private DatabaseManager manager;

    public Tables(DatabaseManager manager) throws DBConnectionException {
        this.manager = manager;
        if (!manager.isConnected()) {
            throw new DBConnectionException();
        }
    }

    @Override
    public String getStatusProcess() {
        try {
            return (format(manager.getTableNames()));
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }

    private String format(Set<String> tables) {
        return tables.toString();
    }
}