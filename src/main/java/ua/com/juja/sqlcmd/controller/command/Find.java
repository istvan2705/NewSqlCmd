package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Find  implements Command {
    private static final String SEPARATOR = "\\,";
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
        try {
            String[] data = command.split("\\|");

            if (data.length != 2 && data.length != 4) {
                view.write(String.format("Error entering command '%s'. Should be " +
                        "'find|tableName' or 'find|tableName|limit|offset'", command));
                    return;
            }
            String tableName = data[1];

            Integer limit = null;
            Integer offset = null;
            if (data.length == 4) {
                limit = Integer.valueOf(data[2]);
                offset = Integer.valueOf(data[3]);
            }

            String[] tableData = manager.getTableData(tableName, limit, offset);
            view.write(formatTable(tableData));

        } catch (SQLException e) {
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