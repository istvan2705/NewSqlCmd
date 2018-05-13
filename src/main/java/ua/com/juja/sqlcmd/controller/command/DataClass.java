package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;

import java.util.Arrays;
import java.util.List;

import static ua.com.juja.sqlcmd.controller.command.Command.SEPARATOR;

class DataClass {
    private static final int FIRST_COLUMN = 2;
    private DataSet set = new DataSet();

    String getTableName(List<String> data) {
        return data.get(1);
    }

    List<String> getTableData(String command) {
        return Arrays.asList(command.split(SEPARATOR));
    }

    DataSet getDataSet(List<String> data) {
        for (int i = FIRST_COLUMN; i < data.size(); i++) {
            set.put(data.get(i), data.get(++i));
        }
        return set;
    }

    DataSet getColumns(List<String> data) {
        for (int i = FIRST_COLUMN; i < data.size(); i++) {
            set.put(data.get(i), i);
        }
        return set;
    }
}




