package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputSet;

import java.sql.SQLException;
import java.util.*;

public class Insert implements Command {
    private InputSet inputSet;
    private DatabaseManager manager;
    private DataSet data = new DataSetImpl();

    public Insert(InputSet inputSet, DatabaseManager manager) {
        this.inputSet = inputSet;
        this.manager = manager;
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = inputSet.getNumberOfParameters();
        if (numberOfParameters < 6 || numberOfParameters % 2 == 1) {
            return "ERROR_ENTERING_MESSAGE" + "'insert|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN";
        }
        String tableName = inputSet.getTableName();
        List<String> columns = data.getColumns();
        List<String> rows = data.getRows();
        try {
            manager.insert(tableName, columns, rows);
            return String.format("Statement are added into the table '%s'", tableName);
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}