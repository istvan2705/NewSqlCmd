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
    public String getStatusProcess() {
        List<String> parameter = data.getParameters();
        if (parameter.size() != 2) {
            return ERROR_ENTERING_MESSAGE + "'find|tableName'";

        }
        String tableName = data.getTableName();
        try {
            Set<String> columns = manager.getColumnsNames(tableName);
            String column = printColumnsNames(columns);
            List<DataSetImpl> rows = manager.getTableRows(tableName);
            String row = printAllRows(rows);
            return column + "\n" + row;


        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }

    private String printColumnsNames(Set<String> columns) {
        StringBuilder result = new StringBuilder();
        for (String column : columns) {
            result.append("|").append(column).append("|");
        }
        return
                "--------------------------" + "\n" +
                        result.toString() + "\n" +
                        "--------------------------";
    }

    private String printAllRows(List<DataSetImpl> tableRows) {
        StringBuilder allRows = new StringBuilder();
        for (DataSetImpl row : tableRows) {
             allRows.append("\n").append(printRow(row));
               }
        return allRows +"\n"+
                "--------------------------";
             }

    private String printRow(DataSetImpl row) {
        StringBuilder result = new StringBuilder();
        List<String> values = row.getValues();
        for (Object value : values) {
            result.append("|").append(value).append("|");
        }
        return result.toString();
    }

}