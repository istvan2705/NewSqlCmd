package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.*;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;


class MainController {
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
            InputWrapper.setInput(input);

            try {
                String commandName = InputWrapper.getCommand();
                SqlCommand sqlCommand = SqlCommand.getSqlCommand(commandName);
                switch (sqlCommand) {
                    case CONNECT:
                        command = new Connect(manager, view);
                        break;

                    case INSERT:
                        command = new Insert(manager, view);
                        break;

                    case TABLES:
                        command = new Tables(manager, view);
                        break;

                    case DROP:
                        command = new Drop(manager, view);
                        break;

                    case CREATE:
                        command = new Create(manager, view);
                        break;

                    case CLEAR:
                        command = new Clear(manager, view);
                        break;

                    case DELETE:
                        command = new Delete(manager, view);
                        break;

                    case FIND:
                        command = new Find(manager, view);
                        break;

                    case UPDATE:
                        command = new Update(manager, view);
                        break;

                    case HELP:
                        command = new Help(view);
                        break;

                    case EXIT:
                         command = new Exit(view);
                         return;

                }
                command.execute();

            } catch (IllegalArgumentException e) {
                view.write("Not existing command " + input);

            } catch (DBConnectionException e) {
                view.write("You can not use this command until you have established connection to the database");
            }
        view.write("Please enter existing command or help");
        }
    }
}






