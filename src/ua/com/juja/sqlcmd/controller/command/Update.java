package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class Update implements Command {
    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");

        try {
            DataSet set = new DataSet();
            for (int i = 2; i < data.length - 1; i++) {
                set.put(data[i], data[++i]);
            }
            String tableName = data[1];
            String id = data[3];
            if (manager.tableExist(tableName)) {
                manager.update(tableName, set, id);

                String[] columns = manager.getColumnsNames(tableName);
                printColumnsNames(columns);

                DataSet[] rows = manager.getTableRows(tableName);
                printTable(rows);
            } else {
                view.write(String.format("The second parameter '%s' has been entered not correctly", tableName));
            }
        } catch (IndexOutOfBoundsException e) {
            view.write("Error entering command, it should be like update|tableName|column1|value1|column2|value2");

        }
    }
        private void printColumnsNames(String[] columns) {
            String result ="|";
            for (String column : columns) {
                result += column + "|";
            }
            view.write("--------------------------");
            view.write(result);
            view.write("--------------------------");
        }
        private void printTable(DataSet[] tableData) {
            for (DataSet row : tableData) {
                printRow(row);
            }
            view.write("--------------------");
        }

        private void printRow(DataSet row) {
            Object[] values = row.getValues();
            String result = "|";
            for (Object value : values) {
                result += value + "|";
            }
            view.write(result);
        }
    }


