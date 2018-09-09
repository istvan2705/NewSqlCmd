package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;


    class MainController {
    private Command command;
    private View view;
    private DatabaseManager manager;
    private DataSet data;

    MainController(DataSet data, DatabaseManager manager, View view) {
        this.data = data;
        this.view = view;
        this.manager = manager;
    }

    void run() {
        view.write("Hello user!");
        view.write("Please enter database, username and password in a format: connect|database|userName|password");
        while (true) {
            String input = view.read();
            String commandName = data.getCommand(input);
            try {
                SqlCommand sqlCommand = SqlCommand.valueOf(commandName.toUpperCase());
                switch (sqlCommand) {
                    case CONNECT:
                        command = new Connect(data, manager, view) {
                        };
                        break;

                    case INSERT:
                        command = new Insert(data, manager, view);
                        break;

                    case TABLES:
                        command = new Tables(manager, view);
                        break;

                    case DROP:
                        command = new Drop(data, manager, view);
                        break;

                    case CREATE:
                        command = new Create(data, manager, view);
                        break;

                    case CLEAR:
                        command = new Clear(data, manager, view);
                        break;

                    case DELETE:
                        command = new Delete(data, manager, view);
                        break;

                    case FIND:
                        command = new Find(data, manager, view);
                        break;

                    case UPDATE:
                        command = new Update(data, manager, view);
                        break;

                    case EXIT:
                        command = new Exit(view);
                        break;

                    case HELP:
                        command = new Help(view);
                        break;
                }
                command.process(input);

            } catch (IllegalArgumentException e) {
                new Unsupported(view).process(input);

            } catch (NullPointerException e) {
                new IsConnected(manager, view).process(input);
            }
              catch (ExitException e) {
                e.getMessage();
            }

            view.write("Please enter existing command or help");
        }
    }
}





