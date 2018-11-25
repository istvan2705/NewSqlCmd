package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InputWrapper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Create implements Command {


    private DatabaseManager manager;
    private View view;
    public Create(DatabaseManager manager, View view) {
           this.manager = manager;
           this.view = view;

    }

    @Override
    public void execute(String command) {
        int numberOfParameters = InputWrapper.getNumberOfParameters(command);
        if (numberOfParameters < 4) {
            view.write( "'create|tableName|column1|column2|...|columnN'");
        }
        String tableName = InputWrapper.getTableName(command);
        List<String> values = InputWrapper.getTableData(command);

        try {
            manager.create(tableName, values);
            view.write(String.format("The table '%s' has been created", tableName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }
}
