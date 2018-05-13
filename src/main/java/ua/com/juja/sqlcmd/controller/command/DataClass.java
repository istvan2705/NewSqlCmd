package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;

import java.util.Arrays;
import java.util.List;

import static ua.com.juja.sqlcmd.controller.command.Command.SEPARATOR;


class DataClass {

    String getTableName(List<String> data) {
        return data.get(1);
    }

    List<String> getTableData(String command) {
        return Arrays.asList(command.split(SEPARATOR));
    }

    DataSet getDataSet(List<String> data) {
        DataSet set = new DataSet();
        for (int i = 2; i < data.size(); i++) {
            set.put(data.get(i), data.get(++i));
        }
        return set;
    }

    DataSet getData(List<String> data) {

        DataSet columns = new DataSet();
        for (int i = 2; i < data.size(); i++) {
            columns.add(data.get(i));
        }
        return columns;
    }
}




