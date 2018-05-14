package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainController {

    private View view;
    private DatabaseManager manager;

    MainController() {
        this.view = new Console();
        this.manager = new JDBCDatabaseManager();
    }

    public void run()  {
        view.write("Hello user!");
        view.write("Please enter database, username and password in a format: connect|database|userName|password");
        while (true) {
            String input = view.read();
            if (input == null) {
                new Exit(view).process(null);
            }
            List<Command> commandList = new ArrayList<>();
            commandList.add(new Connect(manager, view));
            commandList.add(new Exit(view));
            commandList.add(new Help(view));
            commandList.add(new isConnected(manager, view));
            commandList.add(new Create(manager, view));
            commandList.add(new Clear(manager, view));
            commandList.add(new Delete(manager, view));
            commandList.add(new Drop(manager, view));
            commandList.add(new Find(manager, view));
            commandList.add(new Insert(manager, view));
            commandList.add(new Tables(manager, view));
            commandList.add(new Update(manager, view));
            commandList.add(new Unsupported(view));
            CommandsManager commandsManager = new CommandsManager(commandList);
            commandsManager.result(input);


            view.write("Please enter command input(or help):");
        }
    }
}


