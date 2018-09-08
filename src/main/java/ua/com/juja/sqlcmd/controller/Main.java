package ua.com.juja.sqlcmd.controller;


import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

public class Main {

    public static void main(String[] args) {
    DataSet data = new DataSetImpl();
    DatabaseManager manager = new JDBCDatabaseManager();
    View view = new Console();

        MainController controller = new MainController(data, manager, view);
        controller.run();
    }
    }

