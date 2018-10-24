package ua.com.juja.sqlcmd.controller.command;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;

import java.sql.SQLException;

public class Drop implements Command {


    private DatabaseManager manager;

    public Drop(DatabaseManager manager) throws DBConnectionException {
         this.manager = manager;
        this.manager.isConnected();
        if (!manager.isConnected()){
            throw new DBConnectionException();
        }
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters != 2) {
            return ERROR_ENTERING_MESSAGE + "'drop|tableName'";

        }
        String tableName = InputWrapper.getTableName();

        try {
            manager.deleteTable(tableName);
            return String.format("The table '%s' has been deleted", tableName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}
