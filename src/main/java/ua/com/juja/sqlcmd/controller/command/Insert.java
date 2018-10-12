package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.*;

public class Insert implements Command {
    private DataSet data;
    private DatabaseManager manager;
    private View view;

    public Insert(DataSet data, DatabaseManager manager, View view) {
        this.data = data;
        this.manager = manager;
        this.view = view;
    }

    @Override
    public String getStatusProcess() {
        List<String> parameters = data.getParameters();
        if (parameters.size() < 6 || parameters.size() % 2 == 1) {
            return String.format(ERROR_ENTERING_MESSAGE + "'insert|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN");
                   }
        String tableName = data.getTableName();
        List<String> values = data.getTableData();
        Map<String, String> map = data.getValuesForColumns(values);
        try {
            manager.insert(tableName, map);
            return String.format("Statement are added into the table '%s'", tableName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}