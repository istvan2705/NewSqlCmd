package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.*;

public class Insert extends DataClass implements Command {
    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        List<String> data = getTableData(command);
        if (data.size() < 6 || data.size() % 2 == 1) {
            view.write(String.format("Error entering command '%s'. Should be 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN", command));
            return;
        }
        String tableName = getTableName(data);
        DataSet set = getDataSet(data);
        try {
            manager.insert(tableName, set);
            view.write(String.format("Statement are added into the table '%s'", tableName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }


}