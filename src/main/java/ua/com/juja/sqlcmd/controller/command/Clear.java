package ua.com.juja.sqlcmd.controller.command;


import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputSet;

import java.sql.SQLException;


public class Clear  implements Command {

    private InputSet inputSet;
    private DatabaseManager manager;

    public Clear(InputSet inputSet, DatabaseManager manager) {
        this.inputSet = inputSet;
        this.manager = manager;
        }

    @Override
    public String getStatusProcess() {
      int numberOfParameters  = inputSet.getNumberOfParameters();
        if (numberOfParameters != 2) {
            return ERROR_ENTERING_MESSAGE + "'clear|tableName'";
            }
        String tableName = inputSet.getTableName();
        try {
            boolean isCleared = manager.clear(tableName);
            if (isCleared) {
                return String.format("The content of table '%s' has been deleted", tableName);
           }
           else {
                return "You are trying to clear the contents of an empty table";
            }
        } catch (SQLException e) {
           return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());

        }
    }
}