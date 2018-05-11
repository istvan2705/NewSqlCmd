package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Delete implements Command {
    private DatabaseManager manager;
    private View view;

    public Delete(DatabaseManager manager, View view) {//TODO зробити класс Singleton
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void process(String command) {//TODO зробити ключові поля та

        String[] data = command.split(SEPARATOR);//TODO зробити класс який парсить комадну ENUM

        if (data.length != 4) {
            view.write(String.format("Error entering command '%s'. Should be delete|tableName|column|value", command));
            return;
        }
        String tableName = data[1];
        String columnName = data[2];
        String rowName = data[3];

        try {
            boolean isDeleted = manager.deleteRows(tableName, columnName, rowName);
            if (isDeleted) {

                view.write("The row has been deleted");

            } else
                view.write(String.format("Error entering command. The row with rowName  '%s' does not exist", rowName));
        } catch (SQLException e) {
            view.write(String.format(SQL_EXCEPTION_MESSAGE, e.getMessage()));
        }


    }

}