package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Update implements Command {

    private DatabaseManager manager;
    private View view;
    private DataSet data = new DataSetImpl();

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
        List<String> parameters = data.getParameters(command);
        if (parameters.size() < 6 || parameters.size() % 2 == 1) {
            view.write(String.format(ERROR_ENTERING_MESSAGE + "'update|tableName|column1|value1|" +
                    "column2|value2|...|columnN|valueN'", command));
            return;
        }
        String tableName = data.getTableName(command);
        List<String> values = data.getTableData(command);
        String updatedColumn = values.get(0);
        String updatedValue = values.get(1);
        Map<String, String> set = data.setUpdatedValuesToColumns(values);

        try {
            boolean isUpdate = manager.update(tableName, updatedColumn, updatedValue, set);
            if (isUpdate) {
                view.write("The row has been updated");
            } else {
                view.write("The row has been not updated due to incorrect parameter");
            }
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }


}
