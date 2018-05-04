package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Find  implements Command {
    private static final String SEPARATOR = "\\|";
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
            String[] data = command.split(SEPARATOR);
        try {
            if (data.length != 2) {
               view.write(String.format("Error entering command '%s'. Should be "+
                        "'find|tableName'", command));

            }
            String tableName = data[1];

            Set<String> columns = manager.getColumnsNames(tableName);
            printColumnsNames(columns);

            List<DataSet> rows = manager.getTableRows(tableName);
            printTable(rows);



        } catch (SQLException e) {
            view.write(String.format("Can not execute command  due to: %s", e.getMessage()));

        }
    }

    private void printColumnsNames(Set<String> columns) {
        String result = "|";
        for (String column : columns) {
            result += column + "|";
        }
        view.write("--------------------------");
        view.write(result);
        view.write("--------------------------");
    }

    private void printTable(List<DataSet> tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
        view.write("--------------------");
    }


    private void printRow(DataSet row) {
        List<Object> values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }
}
