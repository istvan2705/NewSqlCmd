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
    private InputSet inputSet = new InputSet();

    MainController() {
    }

    void run() {
        view.write("Hello user!");
        view.write("Please enter database, username and password in a format: connect|database|userName|password");
        while (true) {
            String input = view.read();
            inputSet.setInput(input);
          try{
                  SqlCommand sqlCommand = inputSet.getCommand();
                    switch (sqlCommand) {
                        case connect:
                            command = new Connect(inputSet, manager);
                            break;

                        case insert:
                            command = new Insert(inputSet, manager);
                            break;

                        case tables:
                            command = new Tables(manager);
                            break;

                        case drop:
                            command = new Drop(inputSet, manager);
                            break;

                        case create:
                            command = new Create(inputSet, manager);
                            break;

                        case clear:
                            command = new Clear(inputSet, manager);
                            break;

                        case delete:
                            command = new Delete(inputSet, manager);
                            break;

                        case find:
                            command = new Find(inputSet, manager);
                            break;

                        case update:
                            command = new Update(inputSet, manager);
                            break;

                        case exit:
                            command = new Exit();
                            break;

                        case help:
                            command = new Help();
                            break;
                    }

                    view.write(command.getStatusProcess());

                } catch (IllegalArgumentException e) {
                   view.write("Not existing command "+ input);
                } catch (NullPointerException e) {
                    new IsConnected().getStatusProcess();
                } catch (ExitException e) {
                    e.getMessage();
                }


                view.write("Please enter existing command or help");
            }
        }
    }





