package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;


class MainController {
    private Command command;
    private View view = new Console();
    private DatabaseManager manager = new JDBCDatabaseManager();
    private DataSet data = new DataSetImpl();

    MainController() {
    }

    void run() {
        view.write("Hello user!");
        view.write("Please enter database, username and password in a format: connect|database|userName|password");
        while (true) {
            String input = view.read();
            data.setInput(input);
          try{
                  SqlCommand sqlCommand = data.getCommand();
                    switch (sqlCommand) {
                        case connect:
                            command = new Connect(data, manager, view);
                            break;

                        case insert:
                            command = new Insert(data, manager, view);
                            break;

                        case tables:
                            command = new Tables(manager, view);
                            break;

                        case drop:
                            command = new Drop(data, manager, view);
                            break;

                        case create:
                            command = new Create(data, manager, view);
                            break;

                        case clear:
                            command = new Clear(data, manager, view);
                            break;

                        case delete:
                            command = new Delete(data, manager, view);
                            break;

                        case find:
                            command = new Find(data, manager, view);
                            break;

                        case update:
                            command = new Update(data, manager, view);
                            break;

                        case exit:
                            command = new Exit(view);
                            break;

                        case help:
                            command = new Help(view);
                            break;
                    }

                    view.write(command.getStatusProcess());

                } catch (IllegalArgumentException e) {
                   view.write("Not existing command "+ input);
                } catch (NullPointerException e) {
                    new IsConnected(manager, view).getStatusProcess();
                } catch (ExitException e) {
                    e.getMessage();
                }


                view.write("Please enter existing command or help");
            }
        }
    }





