package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;


    class MainController {
    private Command commandInstance;
    private View view;
    private DatabaseManager manager;
    private DataSet data = new DataSetImpl();

    MainController(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    void run() {
        view.write("Hello user!");
        view.write("Please enter database, username and password in a format: connect|database|userName|password");
        while (true) {
            String input = view.read();
            String comm = data.getCommand(input);
            try {
                SqlCommand command = SqlCommand.valueOf(comm.toUpperCase());
                switch (command) {
                    case CONNECT:
                        commandInstance = new Connect(manager, view) {
                        };
                        break;

                    case INSERT:
                        commandInstance = new Insert(manager, view);
                        break;

                    case TABLES:
                        commandInstance = new Tables(manager, view);
                        break;

                    case DROP:
                        commandInstance = new Drop(manager, view);
                        break;

                    case CREATE:
                        commandInstance = new Create(manager, view);
                        break;

                    case CLEAR:
                        commandInstance = new Clear(manager, view);
                        break;

                    case DELETE:
                        commandInstance = new Delete(manager, view);
                        break;

                    case FIND:
                        commandInstance = new Find(manager, view);
                        break;

                    case UPDATE:
                        commandInstance = new Update(manager, view);
                        break;

                    case EXIT:
                        commandInstance = new Exit(view);
                        break;

                    case HELP:
                        commandInstance = new Help(view);
                        break;
                }
                commandInstance.process(input);

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





