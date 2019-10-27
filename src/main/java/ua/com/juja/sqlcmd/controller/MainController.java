package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;


class MainController {
    CommandParser commandParser = new CommandParser();
    private Command command;
    private View view = new Console();
    private DatabaseManager manager = new JDBCDatabaseManager();

    MainController() {
    }

    void run() {
        view.write("Hello user!");
        view.write("Please enter database, username and password in a format: connect|database|userName|password");
        while (true) {
            String input = view.read();
            try {
                String commandName = commandParser.getCommandName(input);
                    switch (commandName) {
                    case "connect":
                        command = new Connect(manager, view);
                        break;
                    case "insert":
                        command = new Insert(manager, view);
                        break;
                    case "tables":
                        command = new Tables(manager, view);
                        break;
                    case "drop":
                        command = new Drop(manager, view);
                        break;
                    case "create":
                        command = new Create(manager, view);
                        break;
                    case "clear":
                        command = new Clear(manager, view);
                        break;
                    case "delete":
                        command = new Delete(manager, view);
                        break;
                    case "find":
                        command = new Find(manager, view);
                        break;
                    case "update":
                        command = new Update(manager, view);
                        break;
                    case "help":
                        command = new Help(view);
                        break;
                    case "exit":
                        new Exit(view).execute(input);
                        return;
                }
                command.execute(input);
            } catch (IllegalArgumentException e) {
                view.write("Not existing command " + input);
            } catch (SQLException e) {
                view.write(e.getMessage());
            }
            view.write("Please enter existing command or help");
        }
    }
}






