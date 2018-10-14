package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputSet;
import ua.com.juja.sqlcmd.view.View;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Update implements Command {

    private InputSet inputSet;
    private DatabaseManager manager;
    private DataSet data = new DataSetImpl();

    public Update(InputSet inputSet, DatabaseManager manager) {
        this.inputSet = inputSet;
        this.manager = manager;
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters  = inputSet.getNumberOfParameters();
        if (numberOfParameters < 6 || numberOfParameters % 2 == 1) {
            return ERROR_ENTERING_MESSAGE + "'update|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN'";
             }
        String tableName = inputSet.getTableName();
        List<String> values = inputSet.getTableData();
        List<String> columns = data.getColumns();
        List<String> rows = data.getRows();
        String keyColumn = values.get(0);
        String keyValue = values.get(1);

        try {
            boolean isUpdate = manager.update(tableName, columns, rows, keyColumn, keyValue);
            if (isUpdate) {
                return "The row has been updated";
            } else {
                return "The row has been not updated due to incorrect parameter";
            }
        } catch (SQLException e) {
            return String.format(SQL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }

}
