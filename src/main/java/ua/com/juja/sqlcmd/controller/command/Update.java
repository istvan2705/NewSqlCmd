package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Update implements Command {
    private static final String SEPARATOR = "\\|";
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


        try {
            String[] data = command.split("\\|");


            if (data.length < 6 || data.length %2 == 1) {
                view.write(String.format("Error entering command '%s'. Should be 'update|tableName|column1|value1|column2|value2|...|columnN|valueN", command));
                return;
            }
            String tableName = data[1];
            DataSet set = new DataSet();
            for (int i = 2; i < data.length - 1; i++) {
                set.put(data[i], data[++i]);
            }
            String id = data[3];


           manager.update(tableName, id, set);
            view.write("The row has been updated");

             }
             catch (SQLException e) {
                 view.write(String.format("Can not execute command  due to: %s", e.getMessage()));

             }
        }
    private String formatTable(String[] tableData) {
        int columnsCount = tableData[0].split(SEPARATOR).length;

        int maxSize = getMaxSize(tableData);
        String result = addSeparator(columnsCount, maxSize);
        boolean header = true;
        for (String row : tableData) {
            String[] data = row.split(SEPARATOR);
            result += "|";
            for (String value : data) {
                result += String.format("%-" + maxSize + "s", " " + value);
                result += "|";
            }
            result += "\n";
            if (header) {
                result += addSeparator( columnsCount, maxSize);
                header = false;
            }
        }
        result += addSeparator(columnsCount, maxSize);

        return result;
    }

    private int getMaxSize(String[] tableData) {
        int longestSize = 0;
        for (String row : tableData) {
            String[] data = row.split(SEPARATOR);
            for (String value : data) {
                int length = value.length();
                longestSize = Math.max(length, longestSize);
            }
        }
        return longestSize + 2;
    }

    private String addSeparator(int columnsCount, int maxSize) {
        int separatorLength = columnsCount * maxSize + columnsCount;
        String result = "+";
        for (int i = 0; i <= separatorLength - 2; i++) {
            result += "-";
        }
        result += "+\n";
        return result;
    }

    }


