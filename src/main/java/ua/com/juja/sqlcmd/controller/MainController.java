package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.CommandParser;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class MainController {
    public CommandParser commandParser = new CommandParser();
    private View view = new Console();
    private DatabaseManager manager = new JDBCDatabaseManager();

    public MainController() {
    }

    public void run() {
        view.write("Hello user!");
        view.write("Please enter database, username and password in a format: connect|database|userName|password");
        while (true) {
            try {
                String input = view.read();
                Command command = getCommand(input);
                command.execute(input);
            } catch (IllegalArgumentException e) {
                view.write("Not existing command");
            } catch (SQLException e) {
                view.write(e.getMessage());
            }
            view.write("Please enter existing command or help");
        }
    }

    private Command getCommand(String input) {
        Command command;
        String commandName = commandParser.getCommandName(input);
        switch (commandName) {
            case "connect" -> command = new Connect(manager, view);
            case "insert" -> command = new Insert(manager, view);
            case "tables" -> command = new Tables(manager, view);
            case "drop" -> command = new Drop(manager, view);
            case "create" -> command = new Create(manager, view);
            case "clear" -> command = new Clear(manager, view);
            case "delete" -> command = new Delete(manager, view);
            case "find" -> command = new Find(manager, view);
            case "update" -> command = new Update(manager, view);
            case "help" -> command = new Help(view);
            case "exit" -> command = new Exit(view);
            default -> throw new IllegalArgumentException();
        }
        return command;
    }
}







