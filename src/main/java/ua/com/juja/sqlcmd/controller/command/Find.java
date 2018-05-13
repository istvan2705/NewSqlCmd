package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Find extends DataClass implements Command {

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        List<String> data = getTableData(command);
        try {
            if (data.size() != 2) {
                view.write(String.format("Error entering command '%s'. Should be " +
                        "'find|tableName'", command));
            }
            String tableName = getTableName(data);

            Set<String> columns = manager.getColumnsNames(tableName);
            printColumnsNames(columns);

            List<DataSet> rows = manager.getTableRows(tableName);
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

    private void printTable(List<DataSet> tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
        view.write("--------------------");
    }

    private void printRow(DataSet row) {
    StringBuilder result = new StringBuilder();
        List<Object> values = row.getValues();
        for (Object value : values) {
        result.append("|").append(value).append("|");
        }
        view.write(result.toString());
    }
}
