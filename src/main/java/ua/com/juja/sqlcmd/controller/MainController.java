package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.Command;
import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;


public class MainController {

    private View view;
    private DatabaseManager manager;
    private Command[] commands;

     MainController() {
        this.view = new Console();
        this.manager = new JDBCDatabaseManager();
        this.commands = new Command[] {

                new Connect(manager, view),
                new Exit(view),
                new Help(view),
                new isConnected(manager,view),
                new Create(manager,view),
                new Clear(manager, view),
                new Delete(manager, view),
                new Drop(manager, view),
                new Find(manager, view),
                new Insert(manager, view),
                new Tables(manager, view),
                new Update(manager, view),
                new Unsupported(view)};
    }

    public void run() {
        view.write("Hello user!");
        view.write("Please enter database, username and password in a format: connect|database|userName|password");

        while (true) {
            String input = view.read();
            if (input == null){//null if close application
                new Exit(view).process(input);
            }

            for(Command command: commands){
                if(command.canProcess(input)){
                  command.process(input);
                    break;
                }

            }
            view.write("Please enter command (or help):");
        }
    }

    }


