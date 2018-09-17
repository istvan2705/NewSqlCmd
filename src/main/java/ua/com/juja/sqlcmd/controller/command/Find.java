package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Find implements Command {

    private DataSet data;
    private DatabaseManager manager;
    private View view;

    public Find(DataSet data, DatabaseManager manager, View view) {
        this.data = data;
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void process(String command) {
        List<String> parameter = data.getParameters(command);
        if (parameter.size() != 2) {
            view.write(String.format(ERROR_ENTERING_MESSAGE + "'find|tableName'", command));
            return;
        }
        String tableName = data.getTableName(command);
        try {
            Set<String> columns = manager.getColumnsNames(tableName);
            printColumnsNames(columns);
            List<DataSetImpl> rows = manager.getTableRows(tableName);
            printTable(rows);
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }

    private void printColumnsNames(Set<String> columns) {
    StringBuilder result = new StringBuilder();
             for (String column : columns) {
            result.append("|").append(column).append("|");
        }
        view.write("--------------------------");
        view.write(result.toString());
        view.write("--------------------------");
    }

    private void printTable(List<DataSetImpl> tableData) {
        for (DataSetImpl row : tableData) {
            printRow(row);
        }
        view.write("--------------------------");
    }

    private void printRow(DataSetImpl row) {
    StringBuilder result = new StringBuilder();
        List<String> values = row.getValues();
        for (Object value : values) {
        result.append("|").append(value).append("|");
        }
        view.write(result.toString());
    }
}
