package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class Find  implements Command {
    DatabaseManager manager;
    View view;

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
        try {

            String[] data = command.split("\\|");
           String tableName =  data[1];

            String[] columns = manager.getColumnsNames(tableName);
            printColumnsNames(columns);

            DataSet[] rows = manager.getTableRows(tableName);
            printTable(rows);

        }catch (IndexOutOfBoundsException e){
            view.write("Error entering command, it should be like find|tableName");
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