package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Find implements Command {

    private DatabaseManager manager;
    View view;


   public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
         }

    @Override
    public void execute(String command) {
        int numberOfParameters = InputWrapper.getNumberOfParameters(command);
        if (numberOfParameters != 2) {
            view.write(ERROR_ENTERING_MESSAGE + "'find|tableName'");
        }
        String tableName = InputWrapper.getTableName(command);
        try {
            Set<String> columns = manager.getColumnsNames(tableName);
            String tableHeader = printColumnsNames(columns);
            List<String> rows = manager.getTableRows(tableName);
            String tableContent = printTableRows(rows);
            StringBuilder table = new StringBuilder();
            table.append(tableHeader).append(tableContent);
            view.write(table.toString());
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }

    private String printColumnsNames(Set<String> columns) {
        StringBuilder result = new StringBuilder();
        for (String column : columns) {
            result.append("|").append(column).append("|");
        }
        return "--------------------------" + "\n" +
                result.toString() + "\n" +
               "--------------------------" + "\n";
    }

    private String printTableRows(List<String> rows) {
        StringBuilder result = new StringBuilder();
        for (String row : rows) {
            result.append("|").append(row).append("|");
        }
        return result.toString();
    }
}



