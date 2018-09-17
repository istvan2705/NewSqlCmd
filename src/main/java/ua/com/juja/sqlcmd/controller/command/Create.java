package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Create implements Command {

    private DataSet data;
    private DatabaseManager manager;
    private View view;


    public Create(DataSet data, DatabaseManager manager, View view) {
        this.data = data;
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void process(String command) {
        List<String> parameters = data.getParameters(command);
        if (parameters.size() < 4) {
            view.write(String.format(ERROR_ENTERING_MESSAGE + "'create|tableName|column1|column2|" +
                    "...|columnN'", command));
            return;
        }
        String tableName = data.getTableName(command);
        List<String>values = data.getTableData(command);

        try {
            manager.create(tableName, values);
            view.write(String.format("The table '%s' has been created", tableName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}
