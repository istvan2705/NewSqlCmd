package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;


public class MainController {

    private View view;
    private DatabaseManager manager;
    private Command [] commands;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[] {

                new Connect(manager, view),
                new Exit(view),
                new Help(view),
                new isConnected(manager,view),
                new Create(manager,view),
                new DeleteContent(manager, view),
                new DeleteRow(manager, view),
                new Drop(manager, view),
                new Find(manager, view),
                new Insert(manager, view),
                new List(manager, view),
                new Update(manager, view),
                new Unsupported(view)};
    }

    public void run() {
        view.write("Hello user!");
        view.write("Please enter name of database, username and password in a format: connect|database|userName|password");

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


