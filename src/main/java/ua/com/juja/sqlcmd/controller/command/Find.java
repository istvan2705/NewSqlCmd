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
            return;
        }
        String tableName = InputWrapper.getTableName(command);
        try {
            Set<String> columns = manager.getColumnsNames(tableName);
           printColumnsNames(columns);
            List<String> rows = manager.getTableRows(tableName);
           printTableRows(rows);

        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }

    private void printColumnsNames(Set<String> columns) {
        StringBuilder result = new StringBuilder();
        for (String column : columns) {
            result.append("|").append(column);
        }
        view.write("--------------------------" + "\n" +
                result.toString() + "\n" +
                "--------------------------");
    }

    private void printTableRows(List<String> rows) {
        StringBuilder result = new StringBuilder();
        for (String row : rows) {
           result.append("|").append(row);
        }
        view.write(result.toString()+
                "--------------------------");

    }
}



